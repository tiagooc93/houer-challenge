import React, { useState, useEffect } from "react";
import { Box, Button, Typography } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useNavigate } from "react-router-dom";

function TablePage() {
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const [columns, setColumns] = useState([]);
  const [rows, setRows] = useState([]);
  const [selectionModel, setSelectionModel] = useState({ ids: new Set() });

  const fetchCsvData = async () => {
    console.log("Fetching table data");
    try {
      const res = await fetch("http://localhost:8080/api/school/data", {
        method: "GET",

        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!res.ok) throw new Error("Failed to fetch CSV data");

      const data = await res.json();
      if (!data || data.length === 0) return;

      const headers = Object.keys(data[0]);

      const cols = headers
        .filter((field) => field !== "id" && field !== "csvIndex")
        .map((field) => ({
          field,
          headerName: field,
          width: 150,
          editable: true,
        }));

      //const sortedCols = cols.sort((a, b) => a.field.localeCompare(b.field));
      //const dataWithId = data.map((row) => ({ id: row.id, ...row }));

      const dataWithId = data
        .map((row) => ({ id: row.id, ...row }))
        .sort((a, b) => b.id - a.id);

      setColumns(cols);
      setRows(dataWithId);
    } catch (err) {
      console.error(err);
      alert("Erro ao buscar CSV do backend");
      navigate("/");
    }
  };

  useEffect(() => {
    fetchCsvData();
  }, [navigate]);

  const handleUpdatedRow = async (updatedRow) => {
    console.log("Sending updated data to the backend:", updatedRow);

    try {
      const response = await fetch("http://localhost:8080/api/school/update", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(updatedRow),
      });

      /*  
      if (!response.ok) throw new Error("Failed to update data");
      const data = await response.json();
      console.log("Backend response:", data);
       */

      fetchCsvData();
    } catch (error) {
      console.error("Error:", error);
    }
    return updatedRow;
  };

  const handleAddRow = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/school/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) throw new Error("Failed to update data");

      const data = await response.json();

      const newRow = { id: data["id"] };
      columns.forEach((col) => (newRow[col.field] = ""));
      setRows([newRow, ...rows]);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleRemoveRows = async () => {
    console.log("Selection Model on Remove:", selectionModel);
    const selectedIds = Array.from(selectionModel.ids || []);
    console.log("Rows ids:", selectedIds);
    console.log("Sending delete request");
    try {
      const response = await fetch("http://localhost:8080/api/school/delete", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ schoolIds: selectedIds }),
      });

      if (!response.ok) throw new Error("Delete failed");

      const data = await response.json();

      console.log("response: ", data);
    } catch (error) {
      console.error("Error:", error);
    }

    fetchCsvData();
    setSelectionModel([]);
    console.log("Selection after removal:", selectionModel);
  };

  if (rows.length === 0) {
    return (
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          minHeight: "50vh",
          width: "100vw",
        }}
      >
        <Typography>Carregando dados...</Typography>
      </Box>
    );
  }

  return (
    <Box
      sx={{
        p: 4,
        ml: 12,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "1600px",
        height: "700px",
      }}
    >
      <Typography variant="h4" gutterBottom>
        Tabela - Instalações físicas por Unidade Escolar
      </Typography>

      <Box sx={{ mt: 2 }}>
        <Button variant="outlined" onClick={handleAddRow} sx={{ mr: 2 }}>
          Adicionar Linha
        </Button>
        <Button
          variant="outlined"
          color="error"
          onClick={handleRemoveRows}
          disabled={selectionModel.ids?.size === 0}
        >
          Remover Linhas Selecionadas
        </Button>
      </Box>

      <Box
        sx={{
          mt: 3,
          width: "100%",
          display: "flex",
          justifyContent: "center",
          overflowX: "auto",
        }}
      >
        <Box sx={{ minWidth: 800 }}>
          <DataGrid
            sx={{
              "& .MuiDataGrid-cell": {
                fontSize: "15px",
              },
              "& .MuiDataGrid-columnHeader": {
                fontSize: "17px",
              },
            }}
            rows={rows}
            columns={columns}
            pageSize={5}
            checkboxSelection
            onRowSelectionModelChange={(newSelection) => {
              console.log("Updated selection model:", newSelection);
              setSelectionModel(newSelection);
            }}
            processRowUpdate={handleUpdatedRow}
            onProcessRowUpdateError={(error) => {
              console.error("Erro ao atualizar a linha:", error);
              alert("Erro ao salvar a atualização. Tente novamente.");
            }}
          />
        </Box>
      </Box>
    </Box>
  );
}

export default TablePage;
