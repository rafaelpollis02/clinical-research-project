import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import './RegisterCompany.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-solid-svg-icons';

const Company = () => {
  const navigate = useNavigate();
  const [view, setView] = useState('list');
  const [companies, setCompanies] = useState([]);
  const [formData, setFormData] = useState({
    id: null,
    cnpj: '',
    nome: '',
    descricao: '',
  });
  const [popupMessage, setPopupMessage] = useState('');
  const [localizarClicked, setLocalizarClicked] = useState(false);
  const [showConfirmPopup, setShowConfirmPopup] = useState(false);
  const [companyIdToDelete, setCompanyIdToDelete] = useState(null);
  const [expandedItems, setExpandedItems] = useState([]);

  const handleSwitchView = (newView) => {
    setView(newView);
    if (newView === 'list') {
      setLocalizarClicked(false);
    }
  };

  const handleLocalizarClick = () => {
    setLocalizarClicked(true);
    fetchCompanies();
  };

  const handleGoBack = () => {
    setFormData({
      id: null,
      cnpj: '',
      nome: '',
      descricao: '',
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

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const endpoint = formData.id
        ? `http://localhost:8080/api/v1/enterprise/${formData.id}`
        : 'http://localhost:8080/api/v1/enterprise';

      const method = formData.id ? 'PUT' : 'POST';

      console.log(`Enviando para API: ${method} ${endpoint}`, formData);

      const response = await fetch(endpoint, {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          cnpj: formData.cnpj,
          name: formData.nome,
          description: formData.descricao,
        }),
      });

      if (response.ok) {
        setPopupMessage(`Empresa ${formData.id ? 'editada' : 'adicionada'} com sucesso!`);
        fetchCompanies();
        handleGoBack();
      } else {
        setPopupMessage(`Falha ao ${formData.id ? 'editar' : 'adicionar'} empresa. Status: ${response.status}`);
      }
    } catch (error) {
      setPopupMessage(`Erro: ${error.message}`);
    }
  };

  const handleEdit = (company) => {
    setFormData({
      id: company.id,
      cnpj: company.cnpj,
      nome: company.name,
      descricao: company.description,
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

  const handleDelete = (companyId) => {
    // Define o ID da empresa a ser excluída
    setCompanyIdToDelete(companyId);
    // Exibe o pop-up de confirmação
    setShowConfirmPopup(true);
  };

  const handleConfirmDelete = () => {
    // Implemente a lógica para exclusão
    console.log(`Excluindo empresa com ID: ${companyIdToDelete}`);
    // ... sua lógica de exclusão aqui

    // Fecha o pop-up
    setShowConfirmPopup(false);
  };

  const handleCancelDelete = () => {
    // Cancela a exclusão
    setShowConfirmPopup(false);
  };

  const fetchCompanies = useCallback(async () => {
    try {
      if (localizarClicked) {
        const response = await fetch('http://localhost:8080/api/v1/enterprise');

        if (response.ok) {
          const data = await response.json();
          setCompanies(data);
        } else {
          setPopupMessage(`Falha ao obter a lista de empresas. Status: ${response.status}`);
        }
      }
    } catch (error) {
      setPopupMessage(`Erro: ${error.message}`);
    }
  }, [localizarClicked]);

  useEffect(() => {
    fetchCompanies();
  }, [fetchCompanies, localizarClicked]);

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
              {companies.map((company) => (
                <li key={company.id}>
                  <div className="company-info">
                    <div>
                      <strong>Nome:</strong> {company.name}
                    </div>
                    <div className="visualizar">
                      <button className="visualizar-button" onClick={() => handleToggleExpand(company.id)}>
                        {expandedItems.includes(company.id) ? (
                          <FontAwesomeIcon icon={faEye} />
                        ) : (
                          <FontAwesomeIcon icon={faEyeSlash} />
                        )}
                      </button>
                    </div>
                  </div>
                  {expandedItems.includes(company.id) && (
                    <>
                      <div>
                        <strong>CNPJ:</strong> {company.cnpj}
                      </div>
                      <div>
                        <strong>Descrição:</strong> {company.description}
                      </div>
                      <div className="button-group">
                        <button onClick={() => handleEdit(company)}>Editar</button>
                        <button onClick={() => handleDelete(company.id)}>Excluir</button>
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
            <h3>Cadastro de Empresa</h3>
            <form onSubmit={handleSubmit}>
              <label htmlFor="cnpj">
                CNPJ<span className="required"> *</span>:
              </label>
              <input
                type="text"
                id="cnpj"
                name="cnpj"
                value={formData.cnpj}
                onChange={handleChange}
                required
              />
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
              <label htmlFor="descricao">
                Descrição<span className="required"> *</span>:
              </label>
              <textarea
                id="descricao"
                name="descricao"
                value={formData.descricao}
                onChange={handleChange}
                required
              ></textarea>
              <div className="button-group">
                <button type="submit">
                  {formData.id ? 'Salvar' : 'Salvar'} 
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
          message="Tem certeza que deseja excluir esta empresa?"
          onConfirm={handleConfirmDelete}
          onCancel={handleCancelDelete}
        />
      )}
    </div>
  );
};

export default Company;