import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import './RegisterEstablishment.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

const Establishment = () => {
  const navigate = useNavigate();
  const [view, setView] = useState('list');
  const [establishments, setEstablishments] = useState([]);
  const [formData, setFormData] = useState({
    id: null,
    nome: '',
    logoFile: null,
  });
  const [popupMessage, setPopupMessage] = useState('');
  const [localizarClicked, setLocalizarClicked] = useState(false);
  const [showConfirmPopup, setShowConfirmPopup] = useState(false);
  const [establishmentIdToDelete, setEstablishmentIdToDelete] = useState(null);
  const [expandedItems, setExpandedItems] = useState([]);

  const handleSwitchView = (newView) => {
    setView(newView);
    if (newView === 'list') {
      setLocalizarClicked(false);
    }
  };

  const handleLocalizarClick = () => {
    setLocalizarClicked(true);
    fetchEstablishments();
  };

  const handleGoBack = () => {
    setFormData({
      id: null,
      nome: '',
      logoFile: null,
    });
    handleSwitchView('list');
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      setFormData({
        ...formData,
        logoFile: file,
      });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const formDataToSend = new FormData();
      formDataToSend.append('name', formData.nome);
      formDataToSend.append('logoFile', formData.logoFile);

      const endpoint = formData.id
        ? `http://localhost:8080/api/v1/establishment/${formData.id}`
        : 'http://localhost:8080/api/v1/establishment';

      const method = formData.id ? 'PUT' : 'POST';

      console.log(`Enviando para API: ${method} ${endpoint}`, formDataToSend);

      const response = await fetch(endpoint, {
        method,
        body: formDataToSend,
      });

      if (response.ok) {
        setPopupMessage(`Estabelecimento ${formData.id ? 'editado' : 'adicionado'} com sucesso!`);
        fetchEstablishments();
        handleGoBack();
      } else {
        setPopupMessage(`Falha ao ${formData.id ? 'editar' : 'adicionar'} estabelecimento. Status: ${response.status}`);
      }
    } catch (error) {
      setPopupMessage(`Erro: ${error.message}`);
    }
  };

  const handleEdit = (establishment) => {
    setFormData({
      id: establishment.id,
      nome: establishment.name,
      logoFile: establishment.logoFile,
    });
    handleSwitchView('form');
  };

  const ConfirmPopup = ({ message, onConfirm, onCancel }) => (
    <div className="confirm-popup">
      <p>{message}</p>
      <button onClick={onConfirm}>Sim</button>
      <button onClick={onCancel}>Não</button>
    </div>
  );

  const handleDelete = (establishmentId) => {
    // Define o ID do estabelecimento a ser excluído
    setEstablishmentIdToDelete(establishmentId);
    // Exibe o pop-up de confirmação
    setShowConfirmPopup(true);
  };

  const handleConfirmDelete = () => {
    // Implemente a lógica para exclusão
    console.log(`Excluindo estabelecimento com ID: ${establishmentIdToDelete}`);
    // ... sua lógica de exclusão aqui

    // Fecha o pop-up
    setShowConfirmPopup(false);
  };

  const handleCancelDelete = () => {
    // Cancela a exclusão
    setShowConfirmPopup(false);
  };

  const fetchEstablishments = useCallback(async () => {
    try {
      if (localizarClicked) {
        const response = await fetch('http://localhost:8080/api/v1/establishment');

        if (response.ok) {
          const data = await response.json();
          setEstablishments(data);
        } else {
          setPopupMessage(`Falha ao obter a lista de estabelecimentos. Status: ${response.status}`);
        }
      }
    } catch (error) {
      setPopupMessage(`Erro: ${error.message}`);
    }
  }, [localizarClicked]);

  useEffect(() => {
    fetchEstablishments();
  }, [fetchEstablishments, localizarClicked]);

  const handleToggleExpand = (id) => {
    setExpandedItems((prevExpandedItems) => {
      if (prevExpandedItems.includes(id)) {
        return prevExpandedItems.filter((item) => item !== id);
      } else {
        return [...prevExpandedItems, id];
      }
    });
  };

  const showContent = view === 'list' && localizarClicked;

  return (
    <div className="register-company-container">
      <div className="menu-container">
        <button onClick={() => handleSwitchView('form')}>
          Adicionar <i className="fas fa-user-plus" style={{ verticalAlign: 'top' }}></i>
        </button>
        <br />
        <br />
        <button onClick={handleLocalizarClick}>
          Localizar <i className="fas fa-notes-medical" style={{ verticalAlign: 'top' }}></i>
        </button>
      </div>

      <div className="company-container">
        <div className="top-bar">
          <h2>Healthuture</h2>
          <button className="voltar" onClick={() => navigate('/register')}>
            Voltar
          </button>
        </div>
        {showContent && (
          <div className="content-list">
            <ul>
              {establishments.map((establishment) => (
                <li key={establishment.id}>
                  <div className="company-info">
                    <div>
                      <strong>Nome:</strong> {establishment.name}
                    </div>
                    <div className="visualizar">
                      <button className="visualizar-button" onClick={() => handleToggleExpand(establishment.id)}>
                        {expandedItems.includes(establishment.id) ? (
                          <FontAwesomeIcon icon={faEye} />
                        ) : (
                          <FontAwesomeIcon icon={faEyeSlash} />
                        )}
                      </button>
                    </div>
                  </div>
                  {expandedItems.includes(establishment.id) && (
                    <>
                      <div>
                        <strong>Logo:</strong> {establishment.logoFile}
                      </div>
                      <div className="button-group">
                        <button onClick={() => handleEdit(establishment)}>Editar</button>
                        <button onClick={() => handleDelete(establishment.id)}>Excluir</button>
                      </div>
                    </>
                  )}
                </li>
              ))}
            </ul>
          </div>
        )}

        {view === 'form' && (
          <div>
            <h3>Cadastro de Estabelecimento</h3>
            <form onSubmit={handleSubmit}>
              <label htmlFor="nome">
                Nome<span className="required"> *</span>:
              </label>
              <input
                type="text"
                id="nome"
                name="nome"
                value={formData.nome}
                onChange={handleChange}
                required
              />
              <label htmlFor="logoFile">
                Logo<span className="required"> *</span>:
              </label>
              <input
                type="file"
                id="logoFile"
                name="logoFile"
                onChange={handleFileChange}
                required
              />
              <div className="button-group">
                <button type="submit">
                  {formData.id ? 'Salvar' : 'Adicionar'} 
                </button>
                <button onClick={handleGoBack}>
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        )}
      </div>

      {popupMessage && (
        <div className="popup">
          <span className="close-popup" onClick={() => setPopupMessage('')}>X</span>
          {popupMessage}
        </div>
      )}

      {showConfirmPopup && (
        <ConfirmPopup
          message="Tem certeza que deseja excluir este estabelecimento?"
          onConfirm={handleConfirmDelete}
          onCancel={handleCancelDelete}
        />
      )}
    </div>
  );
};

export default Establishment;
