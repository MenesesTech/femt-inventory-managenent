import React from "react";
import { useApiAuth } from "../../context/AuthContext";
import LogoutButton from "../buttons/LogoutButton";

const MainContent = () => {
  const { auth0User, apiUser, isLoading } = useApiAuth();

  if (isLoading) return <div>Cargando perfil...</div>;

  if (!apiUser) {
    return (
      <div>
        Verificando rol en el sistema...
        <LogoutButton />
      </div>
    );
  }

  return (
    <div>
      <h2>Bienvenido, {auth0User?.name}</h2>
      <p>Email: {auth0User?.email}</p>
      <div>
        <h3>
          Rol en el Sistema: <strong>{apiUser.rol}</strong>
        </h3>
        {apiUser.rol === "ADMIN" && (
          <div
            style={{
              border: "2px solid gold",
              padding: "10px",
              marginTop: "20px",
              maxWidth: "400px",
              margin: "20px auto",
            }}
          >
            <h4>Panel de Administrador</h4>
            <p>Puedes ver este panel porque eres Administrador.</p>
          </div>
        )}
      </div>
      <LogoutButton />
    </div>
  );
};

export default MainContent;
