import { useAuth0 } from "@auth0/auth0-react";
import { createContext, useContext, useEffect, useState } from "react";
import fetchUserData from "../services/usuarioService/fetchUserData";

// Crear el contexto
const AuthContext = createContext();

// Proveedor
export const AuthProvider = ({ children }) => {
  const {
    isAuthenticated,
    isLoading: loadingAuth0,
    user,
    getAccessTokenSilently,
  } = useAuth0();

  // Estados locales
  const [apiUser, setApiUser] = useState(null);
  const [apiClient, setApiClient] = useState(null);
  const [loading, setLoading] = useState(false);

  // Efecto: cuando el usuario se autentica, obtener datos del backend
  useEffect(() => {
    const loadUserData = async () => {
      if (!isAuthenticated) return;

      try {
        setLoading(true);
        // 1️⃣ Obtener el token JWT de Auth0
        const token = await getAccessTokenSilently();

        // 2️⃣ Llamar al backend (Spring Boot)
        const { client, userData } = await fetchUserData(token);

        // 3️⃣ Guardar en estados
        setApiClient(client);
        setApiUser(userData);
      } catch (error) {
        console.error("Error al obtener datos del usuario:", error);
        setApiUser(null);
      } finally {
        setLoading(false);
      }
    };

    loadUserData();
  }, [isAuthenticated, getAccessTokenSilently]);

  // Valor que compartirás con toda la app
  const contextValue = {
    isAuthenticated,
    auth0User: user,
    apiUser,
    apiClient,
    isLoading: loading || loadingAuth0,
  };

  return (
    <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
  );
};

// Hook personalizado
export const useApiAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useApiAuth debe usarse dentro de un AuthProvider");
  }
  return context;
};
