import React, { useState, useEffect } from 'react';
import { Modal, Box, TextField, Button, Typography, Divider, FormControlLabel, Checkbox } from '@mui/material';

const VehicleModal = ({ open, handleClose, onSubmit, initialData = {} }) => {
  const [formData, setFormData] = useState(initialData);

  useEffect(() => {
    setFormData(initialData);
  }, [initialData]);

  const handleChange = (updatedData) => {
    setFormData(updatedData);
  };

  const handleSubmit = () => {
    onSubmit(formData);
    handleClose();
  };

  const handleCancel = () => {
    setFormData(initialData); // Reseta o formulário para os dados iniciais
    handleClose();
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 400,
          bgcolor: 'background.paper',
          boxShadow: 24,
          p: 4,
        }}
      >
        <Typography variant="h6" mb={2}>
          {initialData.id ? 'Editar Veículo' : 'Cadastrar Veículo'}
        </Typography>
        <TextField
          fullWidth
          label="Veículo"
          name="veiculo"
          value={formData.veiculo || ''}
          onChange={(e) => handleChange({ ...formData, veiculo: e.target.value })}
          margin="normal"
        />
        <TextField
          fullWidth
          label="Marca"
          name="marca"
          value={formData.marca || ''}
          onChange={(e) => handleChange({ ...formData, marca: e.target.value })}
          margin="normal"
        />
        <TextField
          fullWidth
          label="Ano"
          name="ano"
          value={formData.ano || ''}
          onChange={(e) => handleChange({ ...formData, ano: e.target.value })}
          margin="normal"
        />
        <TextField
          fullWidth
          label="Cor"
          name="cor"
          value={formData.cor || ''}
          onChange={(e) => handleChange({ ...formData, cor: e.target.value })}
          margin="normal"
        />
        <TextField
          fullWidth
          label="Descrição"
          name="descricao"
          value={formData.descricao || ''}
          onChange={(e) => handleChange({ ...formData, descricao: e.target.value })}
          margin="normal"
        />
        <FormControlLabel
          control={
            <Checkbox
              checked={formData.vendido || false}
              onChange={(e) => handleChange({ ...formData, vendido: e.target.checked })}
              name="vendido"
            />
          }
          label="Vendido"
        />
        <Button
          variant="contained"
          color="primary"
          onClick={handleSubmit}
          fullWidth
        >
          {initialData.id ? 'Atualizar' : 'Cadastrar'}
        </Button>
        <Divider sx={{ marginTop: 2, marginBottom: 2 }}/>
        <Button
          variant="contained"
          color="error"
          onClick={handleCancel}
          fullWidth
        >
          Cancelar
        </Button>
      </Box>
    </Modal>
  );
};

export default VehicleModal;
