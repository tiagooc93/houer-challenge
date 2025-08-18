import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function SimpleAppBar({ login }) {
  const navigate = useNavigate();
  const { logout } = useAuth();

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="fixed" color="transparent" elevation={1}>
        <Toolbar>
          <Typography
            variant="h4"
            noWrap
            component="div"
            sx={{ display: { xs: "none", sm: "block" } }}
          >
            Viewer
          </Typography>

          {!login && (
            <Button
              sx={{ variant: "text", ml: "auto", color: "black" }}
              onClick={() => {
                logout();
                navigate("/login");
              }}
            >
              <Typography>Logout</Typography>
            </Button>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
