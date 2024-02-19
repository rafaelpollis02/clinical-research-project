import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './Register.css';

const Register = () => {
  const navigate = useNavigate();
  const [data, setData] = useState([]);
  const [nome, setNome] = useState('');
  const [idade, setIdade] = useState('');
  navigate('/home');

  const handleAdd = () => {
    const newItem = { nome, idade };
    setData([...data, newItem]);
    setNome('');
    setIdade('');
  };

  const [filtro, setFiltro] = useState('');
  const handleFilter = () => {
    const filteredData = data.filter(item =>
      item.nome.toLowerCase().includes(filtro.toLowerCase())
    );
    setData(filteredData);
  };

  return (
    <div className="register-container">
      <div className="menu-lateral">
        <p>Menu Lateral</p>
        <ul>
          <li><Link to="/home">Voltar</Link></li>
          <li><Link to="/register">Estabelecimento</Link></li>
          <li><Link to="/register">Empresa</Link></li>
          <li><Link to="/register">Usuário</Link></li>
          <li><Link to="/register">Termos</Link></li>
        </ul>
      </div>
      <div className="cadastro-content">
        <h2>Registro</h2>
        <div>
          <label>
            Nome:
            <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} />
          </label>
        </div>
        <div>
          <label>
            Idade:
            <input type="text" value={idade} onChange={(e) => setIdade(e.target.value)} />
          </label>
        </div>
        <button onClick={handleAdd}>Adicionar</button>
        <div>
          <label>
            Filtro:
            <input type="text" value={filtro} onChange={(e) => setFiltro(e.target.value)} />
          </label>
          <button onClick={handleFilter}>Filtrar</button>
        </div>
        <div>
          <h3>Dados Registrados</h3>
          <ul>
            {data.map((item, index) => (
              <li key={index}>{`Nome: ${item.nome}, Idade: ${item.idade}`}</li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default Register;
