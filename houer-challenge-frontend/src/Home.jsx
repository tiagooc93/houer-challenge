import React, { useState } from "react";
import { Box, Button, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import SimpleAppBar from "./AppBar";

function Home() {
  const [fileName, setFileName] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const handleFile = async (event) => {
    const file = event.target.files[0];
    if (!file) return;

    setFileName(file.name);
    localStorage.setItem("fileName", fileName);

    const formData = new FormData();
    formData.append("file", file);

    setIsLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/school/upload", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`,
        },
        body: formData,
      });

      if (!response.ok) {
        throw new Error("Upload failed");
      }

      //const data = await response.json(); // recebe JSON parseado do backend
      //console.log("response:", data);

      navigate("/table");
    } catch (err) {
      console.error("Error uploading CSV:", err);
      alert("Erro ao enviar CSV");
    }
  };

  return (
    <>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          height: "90vh",
          width: "100vw",
        }}
      >
        <SimpleAppBar />
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            minHeight: "90vh",
            width: "100vw",

            gap: 4,
          }}
        >
          <Typography variant="h3">
            {" "}
            Visualizador - Escolas de São Paulo
          </Typography>
          <Typography variant="h5" gutterBottom>
            Fazer Upload de CSV
          </Typography>
          <input
            type="file"
            accept=".csv"
            style={{ display: "none" }}
            id="csv-upload"
            onChange={handleFile}
          />
          {!isLoading && (
            <label htmlFor="csv-upload">
              <Button variant="contained" component="span">
                Import CSV
              </Button>
            </label>
          )}
          {fileName && (
            <Typography variant="body1" sx={{ mt: 2 }}>
              Selected file: {fileName}
            </Typography>
          )}

          {isLoading && <Typography>Enviado dados ...</Typography>}
        </Box>
      </Box>
    </>
  );
}

export default Home;
