
import {  useNavigate } from 'react-router-dom';
import './Register.css';

const Register = () => {
  const navigate = useNavigate();
  navigate('/home');

  return (
    <div className="register-container">
      <div className="top-bar-register">
        <h2>Healthuture</h2>
      </div>
      <div className="Cadastros">
       
        <ul>
          <li><button className="button-link" onClick={() => navigate('/home')}><i className="fas fa-arrow-left"></i>Voltar</button></li>
          <li><button className="button-link" onClick={() => navigate('/register')}><i className="fas fa-hospital"></i>Estabelecimento</button></li>
          <li><button className="button-link" onClick={() => navigate('/register')}><i className="fas fa-building"></i>Empresa</button></li>
          <li><button className="button-link" onClick={() => navigate('/register')}><i className="fas fa-user"></i>Usuário</button></li>
          <li><button className="button-link" onClick={() => navigate('/register')}><i className="fas fa-file-alt"></i>Termos</button></li>
        </ul>
      </div>
    </div>
  );
};

export default Register;
