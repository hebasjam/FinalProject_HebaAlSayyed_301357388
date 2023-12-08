package com.example.finalproject_hebaalsayyed_301357388.presentation.places

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject_hebaalsayyed_301357388.R
import com.example.finalproject_hebaalsayyed_301357388.databinding.FragmentPlacesLocatorBinding
import com.example.finalproject_hebaalsayyed_301357388.domain.dbo.places.PlaceEntity
import com.example.finalproject_hebaalsayyed_301357388.domain.models.request.places.PlaceResult
import com.example.finalproject_hebaalsayyed_301357388.presentation.places.chatting.FragmentPlaceChatting
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPlacesLocator: Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentPlacesLocatorBinding
    private val viewModel: PlacesViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacesLocatorBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupUI()
    }

    private fun setupUI() {
        binding.btnLocate.setOnClickListener {
            val selectedCity = binding.spnrCities.selectedItem.toString()
            val selectedCategory = binding.spnrCategories.selectedItem.toString()
            viewModel.fetchPlaces(selectedCity, selectedCategory)
        }

        binding.btnCheckDB.setOnClickListener {
            googleMap.clear()
            val selectedCity = binding.spnrCities.selectedItem.toString()
            val selectedCategory = binding.spnrCategories.selectedItem.toString()
            viewModel.fetchPlacesByCategory(selectedCategory,selectedCity)
        }

        binding.fabLocateMe.setOnClickListener {
            getCurrentLocation()
        }

        viewModel.placesFromDB.observe(viewLifecycleOwner) { savedPlaces ->
            if (savedPlaces.isNotEmpty()) {
                updateMapWithSavedPlaces(savedPlaces)
            }
        }


        viewModel.placesData.observe(viewLifecycleOwner) { places ->
            updateMapWithPlaces(places.results)
        }
    }
    private fun updateMapWithSavedPlaces(savedPlaces: List<PlaceEntity>) {
        googleMap.clear()
        val markerPlaceMap = HashMap<Marker, PlaceEntity>()

        savedPlaces.forEach { place ->
            val location = LatLng(place.latitude, place.longitude)
            val marker = googleMap.addMarker(MarkerOptions().position(location).title(place.name))
            marker?.let { markerPlaceMap[it] = place }
        }

        googleMap.setOnMarkerClickListener { marker ->
            markerPlaceMap[marker]?.let { place ->
                showSavedPlaceDetails(place)
            }
            true
        }

        if (savedPlaces.isNotEmpty()) {
            moveCameraToLocation(LatLng(savedPlaces.first().latitude, savedPlaces.first().longitude))
        }
    }

    private fun showSavedPlaceDetails(place: PlaceEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle(place.name)
            .setMessage("Saved Place: ${place.name}\nAddress: ${place.formattedAddress}")
            .setPositiveButton("Dismiss", null)
            .setNegativeButton("View More") { dialog, which ->
                navigateToChatFragment("Tell me about${place.name} located at ${place.formattedAddress}")
            }
            .show()
    }


    private fun updateMapWithPlaces(places: List<PlaceResult>) {
        googleMap.clear()
        val markerPlaceMap = HashMap<Marker, PlaceResult>()

        places.forEach { place ->
            val location = LatLng(place.geometry.location.lat, place.geometry.location.lng)

            val marker = googleMap.addMarker(MarkerOptions().position(location).title(place.name))
            marker?.let { markerPlaceMap[it] = place }
        }

        googleMap.setOnMarkerClickListener { marker ->
            markerPlaceMap[marker]?.let { place ->
                showPlaceDetails(place)
            }
            true
        }

        if (places.isNotEmpty()) {
            val location = LatLng(places.first().geometry.location.lat,places.first().geometry.location.lng)
            moveCameraToLocation(location)
        }
    }

    private fun showPlaceDetails(place: PlaceResult) {
        AlertDialog.Builder(requireContext())
            .setTitle(place.name)
            .setMessage("Selected Place: ${place.name}\nAddress: ${place.formatted_address}")
            .setPositiveButton("Dismiss", null)
            .setNegativeButton("View More") { dialog, which ->
                navigateToChatFragment("Tell me about${place.name} located at ${place.formatted_address}")
            }
            .show()
    }

    private fun moveCameraToLocation(location: LatLng) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 12f)
        googleMap.animateCamera(cameraUpdate)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    private fun navigateToChatFragment(placeName: String) {
        val action = FragmentPlacesLocatorDirections.actionFragmentPlacesLocatorToFragmentPlaceChatting(placeName)
        findNavController().navigate(action)
    }

    private fun getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Found the current location.
                    val lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        val userLocation = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)

                        // Move the camera to the user's current location
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, DEFAULT_ZOOM))

                        // Add a marker at the current location
                        googleMap.addMarker(MarkerOptions().position(userLocation).title("My Location"))
                    }
                } else {
                    Log.d("FragmentPlacesLocator", "Current location is null. Using defaults.")
                    Log.e("FragmentPlacesLocator", "Exception: %s", task.exception)
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private const val DEFAULT_ZOOM = 15f
    }
}