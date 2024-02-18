import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HomeScreen.css';

const HomeScreen = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Lógica de logout aqui, por exemplo, limpar o token de autenticação
    // Após o logout, redirecionar para a tela de autenticação
    navigate('/');
  };

  const handleCadastro = () => {
    // Adicione a lógica para navegar para a tela de cadastro
    navigate('/register');
  };

  const handlePesquisaClinica = () => {
    // Adicione a lógica para navegar de volta para a tela de home
    navigate('/home');
  };

  return (
    <div className="home-container">
      <div className="top-bar">
        <h2>Healthuture</h2>
        <button className="logout-button" onClick={handleLogout}>
          Sair
        </button>
      </div>
      <div className="menu-container">
        <div className="menu">
          <div className="menu-item">
            <button className="Cadastro" onClick={handleCadastro}>Cadastro</button>
            <br />
            <button onClick={handlePesquisaClinica}>Pesquisa Clínica</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomeScreen;
