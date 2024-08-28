-- Cria um cliente e um funcionário padrão para testes e desenvolvimentos
INSERT INTO usuario_autenticavel(`contato`, `email`, `nome`, `role`, `senha`) VALUES
('11999999999', 'cliente@gmail.com', 'Cliente', 'CLIENTE', '$2a$10$VTtp5WnmSC8T6/lG1emdzeQ93CjCZP82iBVrQWirGzq29aHaCSfnW');

INSERT INTO cliente(`cnpj`, `contato`, `cpf`, `email`, `nome`, `senha`, `sexo`, `usuario_autenticavel_id`) VALUES 
('11999999999', '12345678912', NULL, 'cliente@gmail.com', 'CLIENTE', '$2a$10$VTtp5WnmSC8T6/lG1emdzeQ93CjCZP82iBVrQWirGzq29aHaCSfnW', NULL, 1);

INSERT INTO usuario_autenticavel(`contato`, `email`, `nome`, `role`, `senha`) VALUES
('11999999999', 'prestador@gmail.com', 'PRESTADOR', 'PRESTADOR', '$2a$10$VTtp5WnmSC8T6/lG1emdzeQ93CjCZP82iBVrQWirGzq29aHaCSfnW');

INSERT INTO prestador(`cnpj`, `contato`, `cpf`, `email`, `nome`, `senha`, `sexo`, `usuario_autenticavel_id`) VALUES 
('11999999999', '99999999999', NULL, 'prestador@gmail.com', 'PRESTADOR', '$2a$10$VTtp5WnmSC8T6/lG1emdzeQ93CjCZP82iBVrQWirGzq29aHaCSfnW', NULL, 2);
