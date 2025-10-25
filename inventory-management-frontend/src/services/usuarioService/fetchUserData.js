import axios from "axios";
import React, { useState } from "react";

const BASE_URL = import.meta.env.VITE_API_BASE_URL + "/auth"; // Api - Backend Spring Boot

/**
 * Obtiene el perfil del usuario desde el backend usando el token JWT de Auth0.
 */
export default async function fetchUserData(token) {
    try {
        // 1️⃣ Crear cliente Axios autenticado
        const client = axios.create({
            baseURL: BASE_URL,
            headers: { Authorization: `Bearer ${token}` }
        })

        // 2️⃣ Llamar al endpoint /perfil en tu API Spring Boot
        setApiClient(client);

        // Llamamos al backend para obtener informacion del Usuario
        const response = await client.get("/perfil");

        return {
            client,
            userData: response.data,
        }
    } catch (error) {
        console.error("Error al obtener perfil del usuario:", error)
        throw error;
    }
}