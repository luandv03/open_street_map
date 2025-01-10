import { useEffect, useState } from "react";
import {
    TextField,
    Stack,
    Button,
    Container,
    Autocomplete,
    Switch,
    CircularProgress,
} from "@mui/material";
import { TextareaAutosize } from "@mui/base/TextareaAutosize";
import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import AddIcon from "@mui/icons-material/Add";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import axios from "axios";
import useDebounce from "hooks/useDebounce";
import { toast } from "react-toastify";

// Fix for default marker icon issue in react-leaflet
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
    iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
    iconUrl: require("leaflet/dist/images/marker-icon.png"),
    shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});

const VisuallyHiddenInput = ({ type, onChange, multiple }) => (
    <input
        type={type}
        onChange={onChange}
        multiple={multiple}
        style={{
            position: "absolute",
            width: "1px",
            height: "1px",
            margin: "-1px",
            padding: 0,
            border: 0,
            clip: "rect(0, 0, 0, 0)",
            overflow: "hidden",
        }}
    />
);

function CreateTourScreen() {
    const [addressFields, setAddressFields] = useState([
        { id: 1, value: "", suggestions: [], loading: false },
    ]);
    const [tour, setTour] = useState({
        name: "",
        price: 0,
        duration: 0,
        active: false,
        description: "",
        imageUrl: "",
        agencyId: 1,
        destinations: [
            {
                id: 1,
            },
        ],
    });

    const handleAddField = () => {
        setAddressFields([
            ...addressFields,
            {
                id: addressFields.length + 1,
                value: "",
                suggestions: [],
                loading: false,
            },
        ]);
    };

    const handleInputChange = (id, value) => {
        const newFields = addressFields.map((field) =>
            field.id === id ? { ...field, value, loading: true } : field
        );
        setAddressFields(newFields);
    };

    const fetchSuggestions = async (id, query) => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/destination/search?name=${query}`
            );
            const data = response.data;
            const newFields = addressFields.map((field) =>
                field.id === id
                    ? { ...field, suggestions: data, loading: false }
                    : field
            );
            setAddressFields(newFields);
        } catch (error) {
            console.error("Failed to fetch suggestions:", error);
            const newFields = addressFields.map((field) =>
                field.id === id
                    ? { ...field, suggestions: [], loading: false }
                    : field
            );
            setAddressFields(newFields);
        }
    };

    const debouncedAddressFields = useDebounce(addressFields, 500);

    useEffect(() => {
        debouncedAddressFields.forEach((field) => {
            if (field.value.length > 2) {
                fetchSuggestions(field.id, field.value);
            }
        });
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [debouncedAddressFields]);

    const handleCreateTour = async () => {
        try {
            const newTour = {
                ...tour,
                imageUrl:
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2QXeyvtDr_ob6q2v9F9PfBfYsn4uvng2bdg&s",
                agencyId: 1,
                destinations: addressFields
                    .filter((field) => field?.value)
                    .map((field) => ({ id: field.id })),
            };

            if (
                !newTour.name ||
                !newTour.price ||
                !newTour.duration ||
                !newTour.destinations.length
            ) {
                toast.error("Please fill in all fields!");
                return;
            }

            const response = await axios.post(
                "http://localhost:8080/api/tour/create",
                tour
            );

            if (response.status === 201) {
                toast.success("Tour created successfully!");
            }
        } catch (error) {
            console.error("Failed to create tour:", error);
            toast.error("Failed to create tour!");
        }
    };

    return (
        <div>
            <h3>Thông tin tour</h3>
            <Stack direction="row" spacing={10}>
                <TextField
                    id="tourName"
                    label="Tên"
                    variant="outlined"
                    value={tour.name}
                    onChange={(event) =>
                        setTour({ ...tour, name: event.target.value })
                    }
                />
                <TextField
                    id="duration"
                    label="Thời lượng"
                    variant="outlined"
                    value={tour.duration}
                    onChange={(event) =>
                        setTour({ ...tour, duration: event.target.value })
                    }
                />
                <TextField
                    id="price"
                    label="Giá"
                    variant="outlined"
                    value={tour.price}
                    onChange={(event) =>
                        setTour({ ...tour, price: event.target.value })
                    }
                />
            </Stack>
            <Stack direction="row" spacing={30} marginTop={10}>
                <Stack direction="row" spacing={2} alignItems={"center"}>
                    <label htmlFor="">Trạng thái</label>
                    <Switch
                        defaultChecked
                        onChange={(event) =>
                            setTour({ ...tour, active: event.target.checked })
                        }
                    />
                </Stack>

                <Stack direction="row" spacing={2} alignItems={"center"}>
                    <label htmlFor="">Ảnh</label>
                    <Button
                        component="label"
                        role={undefined}
                        variant="contained"
                        tabIndex={-1}
                        startIcon={<CloudUploadIcon />}
                    >
                        Chọn ảnh
                        <VisuallyHiddenInput
                            type="file"
                            onChange={(event) =>
                                console.log(event.target.files)
                            }
                            multiple
                        />
                    </Button>
                </Stack>
            </Stack>

            <Stack marginTop={4}>
                <label htmlFor="">Mô tả</label>
                <TextareaAutosize
                    aria-label="minimum height"
                    minRows={6}
                    placeholder="Đây là tour du lịch..."
                    style={{ width: "100%" }}
                    value={tour.description}
                    onChange={(event) =>
                        setTour({ ...tour, description: event.target.value })
                    }
                />
            </Stack>

            <h3>Lộ trình</h3>
            <Stack direction="row" spacing={4}>
                <Stack spacing={2}>
                    {addressFields.map((field) => (
                        <Stack
                            key={field.id}
                            direction="row"
                            spacing={2}
                            alignItems={"center"}
                        >
                            <Autocomplete
                                sx={{
                                    width: "200px", // 🌟 Tăng độ rộng ở đây
                                }}
                                freeSolo
                                id={`address-${field.id}`}
                                options={field.suggestions.map(
                                    (item) => item.name
                                )}
                                value={field.value}
                                onInputChange={(event, value) =>
                                    handleInputChange(field.id, value)
                                }
                                loading={field.loading}
                                noOptionsText="Không có địa chỉ nào phù hợp"
                                renderInput={(params) => (
                                    <TextField
                                        {...params}
                                        label="Nhập địa chỉ"
                                        variant="outlined"
                                        InputProps={{
                                            ...params.InputProps,
                                            endAdornment: (
                                                <>
                                                    {field.loading ? (
                                                        <CircularProgress
                                                            color="inherit"
                                                            size={20}
                                                        />
                                                    ) : null}
                                                    {
                                                        params.InputProps
                                                            .endAdornment
                                                    }
                                                </>
                                            ),
                                        }}
                                    />
                                )}
                            />
                            <AddIcon
                                style={{ cursor: "pointer" }}
                                onClick={handleAddField}
                            />
                        </Stack>
                    ))}
                </Stack>

                <Container maxWidth="xl" p={10}>
                    <MapContainer
                        style={{ height: "500px", width: "100%" }}
                        center={[21.0061882, 105.8405558]}
                        zoom={13}
                    >
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        {addressFields.map(
                            (field) =>
                                field.latitude &&
                                field.longitude && (
                                    <Marker
                                        key={field.id}
                                        position={[
                                            field?.latitude,
                                            field?.longitude,
                                        ]}
                                    >
                                        <Popup>{field?.value}</Popup>
                                    </Marker>
                                )
                        )}
                    </MapContainer>
                </Container>
            </Stack>

            <Button marginTop={4} primary onClick={() => handleCreateTour()}>
                Tạo tour
            </Button>
        </div>
    );
}

export default CreateTourScreen;
