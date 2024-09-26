"use client"; 

import { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Typography, Grid, Card, CardContent, CircularProgress } from '@mui/material';

const Veiculos = () => {

  interface Veiculo {
    id: number;
    veiculo: string;
    marca: string;
    ano: number;
    vendido: boolean;
    descricao: string;
    createdData: string;
    updatedData: string;
  }
  
  const [listaVeiculos, setListaVeiculos] = useState<Veiculo[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchVeiculos = async () => {
      try {
        const response = await axios.get('http://localhost:8080/veiculos');
        setListaVeiculos(response.data.result);
      } catch (error) {
        console.error('Erro ao buscar veículos:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchVeiculos();
  }, []);

  if (loading) {
    return (
      <Container>
        <CircularProgress />
      </Container>
    );
  }

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        J Garage
      </Typography>
      <Grid container spacing={3}>
        {listaVeiculos.map((list) => (
          <Grid item xs={12} sm={6} md={4} key={list.id}>
            <Card>
              <CardContent>
                <Typography variant="h5">{list.veiculo}</Typography>
                <Typography color="textSecondary">Marca: {list.marca}</Typography>
                <Typography color="textSecondary">Ano: {list.ano}</Typography>
                <Typography color="textSecondary">
                  Vendido: {list.vendido ? 'Sim' : 'Não'}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default Veiculos;
