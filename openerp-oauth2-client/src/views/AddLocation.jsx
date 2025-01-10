import { useEffect, useState } from "react";
import {
    TextField,
    Stack,
    Button,
    Container,
    Autocomplete,
    CircularProgress,
} from "@mui/material";
import AddLocationAltIcon from "@mui/icons-material/AddLocationAlt";
import { MapContainer, TileLayer, Marker, Popup, useMap } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import { ToastContainer, toast } from "react-toastify";
import axios from "axios";
import useDebounce from "hooks/useDebounce";

// Fix for default marker icon issue in react-leaflet
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
    iconUrl: require("leaflet/dist/images/marker-icon.png"),
    shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});
function AddLocationScreen() {
    const [longitude, setLongitude] = useState("");
    const [latitude, setLatitude] = useState("");
    const [address, setAddress] = useState("");
    const [suggestions, setSuggestions] = useState([]);
    const [selectedPosition, setSelectedPosition] = useState([51.505, -0.09]); // Default position
    const [selectedAddress, setSelectedAddress] = useState({
        name: "",
        description: "",
        imageUrl: "",
        latitude: "",
        longitude: "",
    });
    const debouncedAddress = useDebounce(address, 500);
    const [loading, setLoading] = useState(false);

    // Fetch suggestions from Nominatim API
    const fetchSuggestions = async (query) => {
        setLoading(true);
        try {
            const response = await fetch(
                `https://nominatim.openstreetmap.org/search?q=${query}&format=json&addressdetails=1`
            );
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            const data = await response.json();
            console.log(data);
            setSuggestions(Array.isArray(data) ? data : []);
        } catch (error) {
            console.error("Failed to fetch suggestions:", error);
            setSuggestions([]);
        } finally {
            setLoading(false);
        }
    };

    const handleAddressChange = (event, value) => {
        setAddress(value);

        // Find selected address and update lat/lon
        const selected = suggestions.find(
            (item) => item.display_name === value
        );

        if (selected) {
            console.log(selected);
            setLongitude(selected.lon);
            setLatitude(selected.lat);
            setSelectedPosition([
                parseFloat(selected.lat),
                parseFloat(selected.lon),
            ]);
            setSelectedAddress({
                name: selected.display_name,
                description: selected.display_name,
                imageUrl:
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzxpIRcshGQFsYmrH0YpKm7FWjNXOqjG5b1Q&s",
                latitude: selected.lat,
                longitude: selected.lon,
            });
        }
    };

    useEffect(() => {
        if (debouncedAddress?.length > 2) {
            fetchSuggestions(debouncedAddress);
        }
    }, [debouncedAddress]);

    const handleInputChange = (event) => {
        setAddress(event.target.value);
    };

    const handleCreateLocation = async () => {
        try {
            if (!latitude || !longitude) {
                toast.error("Please fill in all fields!");
                return;
            }

            setSelectedPosition([parseFloat(latitude), parseFloat(longitude)]);

            const response = await axios.post(
                "http://localhost:8080/api/destination/create",
                {
                    name: selectedAddress.name,
                    description: selectedAddress.description,
                    imageUrl: selectedAddress.imageUrl,
                    latitude: selectedAddress.latitude,
                    longitude: selectedAddress.longitude,
                }
            );

            if (response.status === 201) {
                toast.success("Location created successfully!");
            }
        } catch (error) {
            console.error("Failed to create location:", error);
            toast.error("Failed to create location!");
        }
    };

    const MapUpdater = () => {
        const map = useMap();
        useEffect(() => {
            map.setView(selectedPosition, 13);
            // eslint-disable-next-line react-hooks/exhaustive-deps
        }, [selectedPosition, map]);
        return null;
    };

    return (
        <div>
            <Stack direction="row" spacing={40} marginBottom={4}>
                <Stack spacing={2}>
                    <TextField
                        id="longitude"
                        label="Longitude"
                        variant="outlined"
                        value={longitude}
                        onChange={(e) => setLongitude(e.target.value)}
                    />
                    <TextField
                        id="latitude"
                        label="Latitude"
                        variant="outlined"
                        value={latitude}
                        onChange={(e) => setLatitude(e.target.value)}
                    />
                </Stack>

                <Stack spacing={2}>
                    <Autocomplete
                        freeSolo
                        id="address-autocomplete"
                        options={suggestions?.map((item) => item.display_name)}
                        value={address}
                        onInputChange={handleInputChange}
                        onChange={handleAddressChange}
                        loading={loading}
                        noOptionsText="Không có địa chỉ nào phù hợp"
                        renderInput={(params) => (
                            <TextField
                                {...params}
                                label="Địa điểm"
                                variant="outlined"
                                InputProps={{
                                    ...params.InputProps,
                                    endAdornment: (
                                        <>
                                            {loading ? (
                                                <CircularProgress
                                                    color="inherit"
                                                    size={20}
                                                />
                                            ) : null}
                                            {params.InputProps.endAdornment}
                                        </>
                                    ),
                                }}
                            />
                        )}
                    />
                    <Button
                        variant="contained"
                        endIcon={<AddLocationAltIcon />}
                        onClick={() => {
                            handleCreateLocation();
                        }}
                    >
                        Thêm địa điểm
                    </Button>
                </Stack>
            </Stack>
            <Container maxWidth="xl" p={10}>
                <MapContainer
                    style={{ height: "500px", width: "100%" }}
                    center={selectedPosition}
                    zoom={13}
                >
                    <TileLayer
                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    />
                    <Marker position={selectedPosition}>
                        <Popup>
                            Địa điểm đã chọn! <br /> Lat: {latitude}, Lon:{" "}
                            {longitude}
                        </Popup>
                    </Marker>
                    <MapUpdater />
                </MapContainer>
            </Container>
            <ToastContainer />
        </div>
    );
}

export default AddLocationScreen;
