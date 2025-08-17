import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import Login from "./LoginPage.jsx";
import Register from "./RegisterPage.jsx";
import ProtectedRoute from "./ProtectedRoute.jsx";
import Home from "./Home.jsx";
import { AuthProvider } from "./AuthContext.jsx";
import TablePage from "./TablePage.jsx";

const token = localStorage.getItem("token");
const isAuthenticated = token !== null && token !== "";

const router = createBrowserRouter([
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/Register",
    element: <Register />,
  },

  {
    element: <ProtectedRoute isAuthenticated={isAuthenticated} />, // wraps children
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "/table",
        element: <TablePage />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")).render(
  <AuthProvider>
    <StrictMode>
      <RouterProvider router={router} />
    </StrictMode>
  </AuthProvider>
);
