import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './HomeScreen.css';
import axios from 'axios';

const HomeScreen = () => {
  const location = useLocation();
  const userName = location.state?.user;
  const navigate = useNavigate();
  const [fullname, setFullname] = useState('');

  const fetchFullname = useCallback(async () => {
    try {
      const personResponse = await axios.get(`http://localhost:8080/api/v1/person/${encodeURIComponent(userName)}/cpfOrEmail`);
      const retrievedFullname = personResponse.data.fullName;
      setFullname(retrievedFullname);
    } catch (error) {
      console.error('Erro ao obter o nome do usuário:', error);
    }
  }, [userName]);

  useEffect(() => {
    fetchFullname();
  }, [fetchFullname, userName]);

  const handleLogout = () => {
    navigate('/');
  };

  const handleCadastro = () => {
    navigate('/register');
  };

  const handlePesquisaClinica = () => {
    navigate('/home');
  };

  return (
    <div className="home-container">
      <div className="top-bar">
        <h2>Healthuture</h2>
        <div className='usuario-home'>Bem-vindo, {fullname}</div>
        <button className="logout-button" onClick={handleLogout}>
          Sair
        </button>
      </div>
      <div className="menu-container">
        <div className="menu">
          <div className="menu-item">
            <button className="Cadastro" onClick={handleCadastro}>
              <i className="fas fa-user-plus" style={{ verticalAlign: 'top' }}></i> Cadastro
            </button>
            <br />
            <button className="Agenda" onClick={handlePesquisaClinica}>
              <i className="fas fa-notes-medical" style={{ verticalAlign: 'top' }}></i> Agenda
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomeScreen;
