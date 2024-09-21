package com.teama.hopeline.ui.features

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.teama.hopeline.data.model.Incident

@Composable
fun CreateIncidentScreen(
    viewModel: CreateIncidentViewModel = hiltViewModel()
) {
    val mapView = rememberMapViewWithLifecycle()
    ReportIncidentScreen(mapView = mapView, onSaveIncident = {
        viewModel.saveIncident(it)
    })
}

@Composable
fun ReportIncidentScreen(mapView: MapView, onSaveIncident:(Incident) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var noVolunteer by remember { mutableStateOf("") }
    var location by remember { mutableStateOf<LatLng?>(null) }
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var marker by remember { mutableStateOf<Marker?>(null) }
    var selectedType by remember { mutableStateOf("Incident") } // Track selected type
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {

        Text("Select Type:")
        Row(horizontalArrangement = Arrangement.Center) {
            RadioButton(
                selected = selectedType == "Incident",
                onClick = { selectedType = "Incident" }
            )
            Text("Incident")
            RadioButton(
                selected = selectedType == "Hub",
                onClick = { selectedType = "Hub" }
            )
            Text("Hub")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedType == "Incident") {
            Text("Incident Title:")
            TextField(value = title, onValueChange = { title = it })

            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Text("Hub Name:")
            TextField(value = title, onValueChange = { title = it })

            Spacer(modifier = Modifier.height(8.dp))
        }

        Text("Description:")
        TextField(value = description, onValueChange = { description = it })

        Spacer(modifier = Modifier.height(8.dp))

        if (selectedType == "Incident") {
            Text("No of Volunteers:")
            TextField(value = noVolunteer, onValueChange = { noVolunteer = it })

            Spacer(modifier = Modifier.height(8.dp))
        }

        Text("Location: ${location?.latitude}, ${location?.longitude ?: ""}")

        Spacer(modifier = Modifier.height(8.dp))

        AndroidView(factory = { mapView }, modifier = Modifier.fillMaxHeight(0.5f)) {
            mapView.getMapAsync { map ->
                googleMap = map
                map.uiSettings.isZoomControlsEnabled = true

                // Listen for map clicks to place or move the marker
                map.setOnMapClickListener { latLng ->
                    if (marker == null) {
                        marker = map.addMarker(MarkerOptions().position(latLng).draggable(true))
                    } else {
                        marker?.position = latLng
                    }
                    location = latLng
                }

                map.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
                    override fun onMarkerDragStart(marker: com.google.android.gms.maps.model.Marker) {}
                    override fun onMarkerDrag(marker: com.google.android.gms.maps.model.Marker) {}
                    override fun onMarkerDragEnd(marker: com.google.android.gms.maps.model.Marker) {
                        location = marker.position
                    }
                })
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            location?.let {
                if (selectedType == "Incident") {
                    val locationString = "${location!!.latitude},${location!!.longitude}"
                    val incidentData = Incident(
                        title = title,
                        description = description,
                        location = locationString,
                        noOfVolunteer = noVolunteer,
                        isHub = false
                    )
                    onSaveIncident(incidentData)
                   // saveIncident(title, description, it, noOfVolunteer.toInt(), context)
                } else {
                    val locationString = "${location!!.latitude},${location!!.longitude}"

                    val incidentData = Incident(
                        title = title,
                        description = description,
                        location = locationString,
                        noOfVolunteer = "",
                        isHub = false
                    )
                    onSaveIncident(incidentData)
                //    saveHub(title, description, it, context)
                }
            }
        }) {
            Text("Confirm")
        }
    }
}

private fun saveIncident(title: String, description: String, location: LatLng, noVolunteer: Int, context: Context) {
    val locationString = "${location.latitude},${location.longitude}"
    val incidentData = Incident(
        title = title,
        description = description,
        location = locationString,
        noOfVolunteer = "",
        isHub = false
    )

    val db = FirebaseFirestore.getInstance()
    db.collection("incidents").add(incidentData)
        .addOnSuccessListener { Toast.makeText(context, "Incident Saved!", Toast.LENGTH_SHORT).show() }
        .addOnFailureListener { Toast.makeText(context, "Failed to Save Incident!", Toast.LENGTH_SHORT).show() }
}

private fun saveHub(title: String, description: String, location: LatLng, context: Context) {
    val locationString = "${location.latitude},${location.longitude}"

    val hubData = Incident(
        title = title,
        description = description,
        location = locationString,
        isHub = true
    )

    val db = FirebaseFirestore.getInstance()
    db.collection("hubs").add(hubData)
        .addOnSuccessListener { Toast.makeText(context, "Hub Saved!", Toast.LENGTH_SHORT).show() }
        .addOnFailureListener { Toast.makeText(context, "Failed to Save Hub!", Toast.LENGTH_SHORT).show() }
}

// Helper to manage MapView lifecycle within Compose
@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver {
    return remember {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(null)
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> Unit
            }
        }
    }
}
