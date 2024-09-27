"use client"; 

import { useEffect, useState } from 'react';
import axios from 'axios';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { 
  Container, 
  Typography, 
  Box, Card, 
  CardContent, 
  CircularProgress, 
  Button, 
  Pagination, 
  Grid, 
  TextField, 
  Divider, 
  CardActions,
  Accordion, 
  AccordionSummary, 
  AccordionDetails  } from '@mui/material';
import VehicleModal from './components/VehicleModal';
import {distribuicaoPorMarca, distribuicaoProDecada, updateVehicle, cadastradosUltimaSemana, fetchVehicles, deleteVehicle, patchVehicle, addVehicle} from './api/apiService';
import { enqueueSnackbar } from 'notistack';
const Veiculos = () => {

  interface Veiculo{
    id: number;
    veiculo?: string;
    marca?: string;
    ano?: number;
    cor?: string;
    vendido?: boolean;
    descricao?: string;
    createdData?: string;
    updatedData?: string;
  }

  const [isLoadingVeiculos, setIsLoadingVeiculos] = useState(true);
  const [isLoadingDecada, setIsLoadingDecada] = useState(true);
  const [isLoadingFabricante, setIsLoadingFabricante] = useState(true);

  const [vehicles, setVehicles] = useState<Veiculo[]>([]);
  const [openModal, setOpenModal] = useState(false);
  const [selectedVehicle, setSelectedVehicle] = useState<Veiculo | null>(null);
  const [marca, setMarca] = useState("");
  const [ano, setAno] = useState("");
  const [cor, setCor] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
   // Dados de distribuição
  const [distribuicaoDecada, setDistribuicaoDecada] = useState<{ [key: string]: number }>({});
  const [distribuicaoFabricante, setDistribuicaoFabricante] = useState<{ [key: string]: number }>({});

  useEffect(() => {
    fetchDistribuicaoPorDecada().then(() => setIsLoadingDecada(false));
    fetchDistribuicaoPorFabricante().then(() => setIsLoadingFabricante(false));
    searchVehicles(page).then(() => setIsLoadingVeiculos(false));
  }, [page]);


  const fetchDistribuicaoPorDecada = async () => {
    try {
      const response = await distribuicaoProDecada()
      setDistribuicaoDecada(response); // Define a distribuição por década
    } catch (error) {
      console.error('Erro ao buscar distribuição por década:', error);
    }
  };

  const fetchDistribuicaoPorFabricante = async () => {
    try {
      const response = await distribuicaoPorMarca()
      setDistribuicaoFabricante(response); // Define a distribuição por fabricante
    } catch (error) {
      console.error('Erro ao buscar distribuição por fabricante:', error);
    }
  };
  const searchVehicles = async (page: number) => {
    const params = {
      marca: marca || undefined,
      ano: ano || undefined,
      cor: cor || undefined,
      page: page,
    };
    try {
      const response = await fetchVehicles(params);
      setVehicles(response.data.result);
      setTotalPages(response.data.pageTotal);
    } catch (error) {
      enqueueSnackbar('Erro ao buscar veículos: ' + error, { variant: 'error' });
    }
  };

  const searchUltimaSemana = async () => {
    try {
      const response = await cadastradosUltimaSemana();
      setVehicles(response);
    } catch (error) {
      enqueueSnackbar('Erro ao buscar veículos: ' + error, { variant: 'error' });
    }
  }

  const handleDelete = async (id: number) => {
    try {
      await deleteVehicle(id);
      enqueueSnackbar('Veículo deletado  com sucesso!', { variant: 'success' });
      handleSearch();
    } catch (error) {
      enqueueSnackbar('Erro ao deletar veículo' + error, { variant: 'error' });
    }
  };

  const handleSubmit = async (formData: Veiculo) => {
    try {
      if (selectedVehicle) {
        const changedFields: Partial<Record<keyof Veiculo, string | number | boolean>> = {};
  
        (Object.keys(formData) as (keyof Veiculo)[]).forEach((key) => {
          if (formData[key] !== selectedVehicle[key]) {
            changedFields[key] = formData[key] as any; // Cast para any para evitar o erro
          }
        });
  
        if (Object.keys(changedFields).length > 0) {
          await patchVehicle(changedFields.id, patchVehicle);
          enqueueSnackbar('Veículo atualizado com sucesso!', { variant: 'success' });
        } else {
          await updateVehicle(selectedVehicle.id, formData);
          enqueueSnackbar('Veículo atualizado com sucesso!', { variant: 'success' });
        }
      } else {
        await addVehicle(formData);
        enqueueSnackbar('Veículo cadastrado com sucesso!', { variant: 'success' });
        handleSearch();
      }
      handleSearch()
    } catch (error) {
      enqueueSnackbar('Erro ao salvar veículo' + error, { variant: 'error' });
    }
  };
  
  

  const handleEdit = (vehicle: Veiculo) => {
    setSelectedVehicle(vehicle);
    setOpenModal(true);
  };

  const handleAdd = () => {
    setSelectedVehicle(null);
    setOpenModal(true);
  };

  const handleSearch = () => {
    setIsLoadingVeiculos(true)
    setPage(0); 
    searchVehicles(0).then(() => setIsLoadingVeiculos(false));
  };

  const handleSearchUltimaSamana = () => {
    setIsLoadingVeiculos(true)
    searchUltimaSemana().then(() => setIsLoadingVeiculos(false));
  };

  const handlePageChange = (event: any, value: number) => {
    setIsLoadingVeiculos(true)
    setPage(value - 1);
    searchVehicles(value - 1).then(() => setIsLoadingVeiculos(false));
  };

  return (
    <Container>
       <Typography variant="h4" gutterBottom>
        J Garage
      </Typography>
      <Button variant="contained" sx={{ backgroundColor: 'green' }} onClick={handleAdd}>
        Adicionar Veículo
      </Button>
      <Divider sx={{ marginTop: 2, marginBottom: 2 }} />
      {/* Exibindo a Distribuição em Accordions */}
      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="h6">Distribuição de Veículos por Década</Typography>
        </AccordionSummary>
        <AccordionDetails>
        {isLoadingDecada ? (
          <Box display="flex" justifyContent="center" alignItems="center">
            <CircularProgress />
          </Box>
        ) : (
          <Grid container spacing={2}>
            {Object.keys(distribuicaoDecada)?.map((decada) => (
              <Grid item xs={12} sm={6} md={4} key={decada}>
                <Card>
                  <CardContent>
                    <Typography variant="h6">Década de {decada}</Typography>
                    <Typography>{distribuicaoDecada[decada]} veículos</Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        )}
        </AccordionDetails>
      </Accordion>

      <Accordion>
        <AccordionSummary expandIcon={<ExpandMoreIcon />}>
          <Typography variant="h6">Distribuição de Veículos por Fabricante</Typography>
        </AccordionSummary>
        <AccordionDetails>
        {isLoadingFabricante ? (
          <Box display="flex" justifyContent="center" alignItems="center">
            <CircularProgress />
          </Box>
        ) : (
          <Grid container spacing={2}>
            {Object.keys(distribuicaoFabricante).map((fabricante) => (
              <Grid item xs={12} sm={6} md={4} key={fabricante}>
                <Card>
                  <CardContent>
                    <Typography variant="h6">{fabricante}</Typography>
                    <Typography>{distribuicaoFabricante[fabricante]} veículos</Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
          )}
        </AccordionDetails>
      </Accordion>

      <Divider sx={{ margin: '20px 0' }} />
      {/* Filtros */}
      <Grid container spacing={2} marginBottom={2}>
        <Grid item xs={12} sm={4}>
          <TextField
            fullWidth
            label="Marca"
            variant="outlined"
            value={marca}
            onChange={(e) => setMarca(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField
            fullWidth
            label="Ano"
            variant="outlined"
            value={ano}
            onChange={(e) => setAno(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} sm={4}>
          <TextField
            fullWidth
            label="Cor"
            variant="outlined"
            value={cor}
            onChange={(e) => setCor(e.target.value)}
          />
        </Grid>
        <Grid container  sx={{ margin: '5px 0' }} spacing={2} direction="row">
          <Grid item>
            <Button variant="contained" color="primary" onClick={handleSearch}>
              Buscar
            </Button>
          </Grid>
          <Grid item>
            <Button variant="contained" color="primary" onClick={handleSearchUltimaSamana}>
              Cadastrados Ultima Semana
            </Button>
          </Grid>
        </Grid>
      </Grid>
      {isLoadingVeiculos  ? (
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '50vh' }}>
          <CircularProgress />
        </Box>
      ) : (
        <>
      <Box mt={2}>
        {vehicles.map((vehicle) => (
          <Card key={vehicle.id} sx={{ marginBottom: 2 }}>
            <CardContent>
              <Typography variant="h6">{vehicle.veiculo}</Typography>
              <Typography variant="body2">{`Marca: ${vehicle.marca}, Ano: ${vehicle.ano}, Cor: ${vehicle.cor}, Vendido: ${vehicle.vendido ? 'Sim' : 'Não'}`}</Typography>
              <Typography variant="body2">{`Criado em: ${vehicle.createdData}, Atualizado em: ${vehicle.updatedData == null ? "" : vehicle.updatedData }`}</Typography>
              <Typography variant="body2">{vehicle.descricao}</Typography>
              <Button
                variant="contained"
                color="primary"
                onClick={() => handleEdit(vehicle)}
                sx={{ marginRight: 1 }}
              >
                Editar
              </Button>
              <Button
                variant="contained"
                color="error"
                onClick={() => handleDelete(vehicle.id)}
              >
                Deletar
              </Button>
            </CardContent>
          </Card>
        ))}
      </Box>
      
      <VehicleModal
        open={openModal}
        handleClose={() => setOpenModal(false)}
        onSubmit={handleSubmit}
        initialData={selectedVehicle || {}}
      />
    {/* Paginação */}
    <Box sx={{ display: 'flex', justifyContent: 'center', marginTop: 2 }}>
      <Pagination
          count={totalPages}
          page={page + 1}
          onChange={handlePageChange}
          color="primary"
          sx={{ marginTop: 4 }}
        />
    </Box>
    </>
    )}
    </Container>
  );
};

export default Veiculos;
