import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import MainContent from "./components/templates/MainContent";
import Dashboard from "./components/templates/Dashboards";
import ProtectedRoute from "./routes/ProtectedRoute";
import LoginButton from "./components/buttons/LoginButton";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div style={{ textAlign: "center", marginTop: "50px" }}>
          <h1>Sistema de Inventario</h1>
          <LoginButton />
          <Routes>
            <Route path="/" element={<MainContent />} />
            <Route
              path="/dashboard"
              element={
                <ProtectedRoute roles={["ADMIN", "EMPLEADO"]}>
                  <Dashboard />
                </ProtectedRoute>
              }
            />
            {/* Otras rutas */}
          </Routes>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
