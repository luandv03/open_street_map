import React, { useState } from "react";
import {
    OutlinedInput,
    Button,
    List,
    ListItem,
    ListItemText,
    Divider,
} from "@mui/material";

const NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search?";

export default function SearchBox(props) {
    const { selectPosition, setSelectPosition } = props;
    const [searchText, setSearchText] = useState("");
    const [listPlace, setListPlace] = useState([]);

    const handleSearch = async () => {
        const params = new URLSearchParams({
            q: searchText,
            format: "json",
            addressdetails: "1",
        });

        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 60000); // 5 seconds timeout

        try {
            const response = await fetch(
                `${NOMINATIM_BASE_URL}${params.toString()}`,
                {
                    signal: controller.signal,
                }
            );
            clearTimeout(timeoutId);
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            const data = await response.json();
            setListPlace(data);
        } catch (error) {
            if (error.name === "AbortError") {
                console.error("Request timed out");
            } else {
                console.error("Failed to fetch suggestions:", error);
            }
        }
    };

    return (
        <div style={{ display: "flex", flexDirection: "column" }}>
            <div style={{ display: "flex" }}>
                <div style={{ flex: 1 }}>
                    <OutlinedInput
                        style={{ width: "100%" }}
                        value={searchText}
                        onChange={(event) => {
                            setSearchText(event.target.value);
                        }}
                        placeholder="Search for a location"
                    />
                </div>
                <Button
                    onClick={handleSearch}
                    variant="contained"
                    color="primary"
                >
                    Search
                </Button>
            </div>
            <Divider />
            <List>
                {listPlace.map((place, index) => (
                    <ListItem
                        button
                        key={index}
                        onClick={() =>
                            setSelectPosition([place.lat, place.lon])
                        }
                    >
                        <ListItemText primary={place.display_name} />
                    </ListItem>
                ))}
            </List>
        </div>
    );
}
