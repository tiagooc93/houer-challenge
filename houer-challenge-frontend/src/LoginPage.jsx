import {
  TextField,
  Typography,
  Box,
  Button,
  FormControlLabel,
  Checkbox,
} from "@mui/material";

import { useNavigate } from "react-router-dom";
import FormControl from "@mui/material/FormControl";
import { useState, useEffect } from "react";
import { useAuth } from "./AuthContext";
import SimpleAppBar from "./AppBar";

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [remember, setRemember] = useState(true);

  const { isAuthenticated } = useAuth();

  const { login } = useAuth();

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/");
    }
  }, []);

  const handleOnSubmitLogin = async () => {
    console.log("Sending login request");
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        alert("Usuário e/ou Senha Incorretos !");
        throw new Error("Login failed");
      }

      const data = await response.json();
      console.log("response: ", data);
      console.log("Token: ", data.token);

      // Store token if you want
      login(data.token);
      navigate("/");
    } catch (error) {
      console.error("Error:", error);
    }
  };
  return (
    <>
      <SimpleAppBar login="true"></SimpleAppBar>
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        minHeight="80vh"
        minWidth="100vw"
        justifyContent="center"
        gap={2}
      >
        <Typography sx={{ fontSize: 40, fontWeight: "medium" }}>
          Sign in
        </Typography>
        <Box
          component="form"
          onSubmit={handleOnSubmitLogin}
          display="flex"
          flexDirection="column"
          gap={2}
          maxWidth="400px"
          width="100%"
          sx={{ boxShadow: 3, p: 6 }}
        >
          <>
            <FormControl fullWidth margin="normal">
              <TextField
                type="email"
                placeholder="e-mail"
                label="Email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </FormControl>

            <FormControl fullWidth margin="normal">
              <TextField
                type="password"
                placeholder="******"
                label="Password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </FormControl>

            <FormControlLabel
              control={
                <Checkbox
                  checked={remember}
                  onChange={(e) => setRemember(e.target.checked)}
                  color="primary"
                />
              }
              label="Lembrar-me"
            />

            <Button
              variant="contained"
              sx={{ border: 1, mt: 2 }}
              onClick={handleOnSubmitLogin}
            >
              Login
            </Button>
          </>
        </Box>
        <Button
          variant="outlined"
          sx={{ fontSize: 16, border: 0, mt: 2 }}
          onClick={() => navigate("/register")}
        >
          Não tem acesso ? Cadastre-se agora !
        </Button>
      </Box>
    </>
  );
}

export default Login;
