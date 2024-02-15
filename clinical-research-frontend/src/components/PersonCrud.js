import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './PersonCrud.css'; // Importando o arquivo CSS para estilização

const PersonCrud = () => {
  const [persons, setPersons] = useState([]);
  const [formData, setFormData] = useState({
    fullName: '',
    cpf: '',
    rg: '',
    phoneNumber: '',
    email: '',
    birthDate: ''
  });
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    fetchPersons();
  }, []);

  const fetchPersons = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/v1/person');
      setPersons(response.data);
    } catch (error) {
      console.error('Error fetching persons:', error);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Formatar a data para o formato dd/mm/yyyy
      const formattedDate = formData.birthDate.split('-').reverse().join('/');
      const newData = { ...formData, birthDate: formattedDate };

      await axios.post('http://localhost:8080/api/v1/person', newData);
      setFormData({
        fullName: '',
        cpf: '',
        rg: '',
        phoneNumber: '',
        email: '',
        birthDate: ''
      });
      fetchPersons();
      setSuccessMessage('Person saved successfully.');
      setTimeout(() => {
        setSuccessMessage('');
      }, 3000);
    } catch (error) {
      console.error('Error adding person:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/v1/person/${id}`);
      fetchPersons();
    } catch (error) {
      console.error('Error deleting person:', error);
    }
  };

  return (
    <div className="person-crud-container">
      <h1>Persons</h1>
      {successMessage && <div className="success-message">{successMessage}</div>}
      <ul>
        {persons.map((person) => (
          <li key={person.id}>
            {person.fullName} - {person.email}{' '}
            <button onClick={() => handleDelete(person.id)}>Delete</button>
          </li>
        ))}
      </ul>
      <h2>Add Person</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Full Name"
          name="fullName"
          value={formData.fullName}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="CPF"
          name="cpf"
          value={formData.cpf}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="RG"
          name="rg"
          value={formData.rg}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="Phone Number"
          name="phoneNumber"
          value={formData.phoneNumber}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="Email"
          name="email"
          value={formData.email}
          onChange={handleChange}
        />
        <input
          type="date"
          placeholder="Birth Date"
          name="birthDate"
          value={formData.birthDate}
          onChange={handleChange}
        />
        <button type="submit">Add Person</button>
      </form>
    </div>
  );
};

export default PersonCrud;
