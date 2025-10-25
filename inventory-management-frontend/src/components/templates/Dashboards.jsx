import React, { useEffect, useState } from "react";
import { useApiAuth } from "../../context/AuthContext";

const Dashboard = () => {
  const { apiClient, apiUser, isLoading } = useApiAuth();
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    const fetchProductos = async () => {
      if (!apiClient) return;
      try {
        const { data } = await apiClient.get("/productos"); // tu endpoint real
        setProductos(data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchProductos();
  }, [apiClient]);

  if (isLoading) return <div>Cargando...</div>;
  if (!apiUser) return <div>No autorizado</div>;

  return (
    <div>
      <h2>Dashboard</h2>
      <ul>
        {productos.map((p) => (
          <li key={p.id}>{p.nombre}</li>
        ))}
      </ul>
    </div>
  );
};

export default Dashboard;
