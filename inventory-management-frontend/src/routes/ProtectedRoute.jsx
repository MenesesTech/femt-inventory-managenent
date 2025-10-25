import React from "react";
import { Navigate } from "react-router-dom";
import { useApiAuth } from "../context/AuthContext";

const ProtectedRoute = ({ children, roles = [] }) => {
  const { isAuthenticated, apiUser, isLoading } = useApiAuth();

  if (isLoading) return <div>Cargando...</div>;
  if (!isAuthenticated) return <Navigate to="/" replace />;
  if (roles.length && !roles.includes(apiUser?.rol))
    return <div>No autorizado</div>;

  return children;
};

export default ProtectedRoute;
