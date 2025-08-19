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
      setFileName(null);
      setIsLoading(false);
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
            height: "80vh",
            width: "100vw",
            mb: 25,

            gap: 4,
          }}
        >
          <Typography variant="h4">
            {" "}
            Fazer upload do CSV a ser visualizado:
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
              <Button
                variant="contained"
                component="span"
                sx={{ pl: 3, pr: 3 }}
              >
                Importar CSV
              </Button>
            </label>
          )}
          {fileName && (
            <Typography variant="body1" sx={{ mt: 2 }}>
              Selected file: {fileName}
            </Typography>
          )}

          {isLoading && <Typography>Enviando dados ...</Typography>}
        </Box>
      </Box>
    </>
  );
}

export default Home;
