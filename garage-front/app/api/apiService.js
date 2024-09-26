// src/apiService.js

import axios from 'axios';

const API_URL = 'http://localhost:8080/veiculos';

// Função para buscar todos os veículos
export const fetchVehicles = async (params) => {
  const response = await axios.get(`${API_URL}`, { params });
  return response;
};

// Função para adicionar um novo veículo
export const addVehicle = async (vehicle) => {
  const response = await axios.post(API_URL, vehicle);
  return response.data;
};

// Função para atualizar um veículo existente
export const patchVehicle = async (id, updatedFields) => {
  const response = await axios.patch(`${API_URL}/${id}`, updatedFields);
  return response.data;
};

// Função para atualizar um veículo existente
export const updateVehicle = async (id, vehicle) => {
    const response = await axios.put(`${API_URL}/${id}`, vehicle);
    return response.data;
};

// Função para deletar um veículo
export const deleteVehicle = async (id) => {
  const response = await axios.delete(`${API_URL}/${id}`);
  return response.data;
};

// Função para buscar um veículo pelo ID
export const fetchVehicleById = async (id) => {
  const response = await axios.get(`${API_URL}/${id}`);
  return response.data;
};

// Função para buscar distribuição de veículos por década
export const distribuicaoProDecada = async () => {
  const response = await axios.get(`${API_URL}/distribuicao-decadas`);
  return response.data;
};

// Função para buscar distribuição de veículos por marca
export const distribuicaoPorMarca = async () => {
  const response = await axios.get(`${API_URL}/distribuicao-fabricante`);
  return response.data;
};
