-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           5.7.14-log - MySQL Community Server (GPL)
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Copiando estrutura do banco de dados para meubanco
CREATE DATABASE IF NOT EXISTS `meubanco` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `meubanco`;


-- Copiando estrutura para tabela meubanco.fornecedor
CREATE TABLE IF NOT EXISTS `fornecedor` (
  `idFornecedor` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela meubanco.fornecedor: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` (`idFornecedor`, `nome`) VALUES
	(1, 'Kabum');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;


-- Copiando estrutura para tabela meubanco.itensvenda
CREATE TABLE IF NOT EXISTS `itensvenda` (
  `idProduto` int(11) DEFAULT NULL,
  `idVenda` int(11) DEFAULT NULL,
  `valorTotal` int(11) DEFAULT NULL,
  KEY `FK` (`idVenda`),
  KEY `FK_itensVenda_produto` (`idProduto`),
  CONSTRAINT `FK` FOREIGN KEY (`idVenda`) REFERENCES `venda` (`idVenda`),
  CONSTRAINT `FK_itensVenda_produto` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`idProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela meubanco.itensvenda: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `itensvenda` DISABLE KEYS */;
/*!40000 ALTER TABLE `itensvenda` ENABLE KEYS */;


-- Copiando estrutura para tabela meubanco.marca
CREATE TABLE IF NOT EXISTS `marca` (
  `idMarca` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT '0',
  PRIMARY KEY (`idMarca`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela meubanco.marca: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
INSERT INTO `marca` (`idMarca`, `nome`) VALUES
	(1, 'RAZER'),
	(2, 'INTEL'),
	(3, 'Heber'),
	(4, 'Renata'),
	(5, 'SteelSeries');
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;


-- Copiando estrutura para tabela meubanco.produto
CREATE TABLE IF NOT EXISTS `produto` (
  `idProduto` int(11) NOT NULL AUTO_INCREMENT,
  `idMarca` int(10) NOT NULL DEFAULT '0',
  `idTipo` int(10) NOT NULL DEFAULT '0',
  `idFornecedor` int(10) NOT NULL DEFAULT '0',
  `estoqueMinimo` int(10) NOT NULL DEFAULT '5',
  `estoqueAtual` int(50) NOT NULL DEFAULT '0',
  `nome` varchar(50) NOT NULL DEFAULT '0',
  `preco` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idProduto`),
  KEY `FK_produto_marca` (`idMarca`),
  KEY `FK_produto_tipo` (`idTipo`),
  KEY `FK_produto_fornecedor` (`idFornecedor`),
  CONSTRAINT `FK_produto_fornecedor` FOREIGN KEY (`idFornecedor`) REFERENCES `fornecedor` (`idFornecedor`),
  CONSTRAINT `FK_produto_marca` FOREIGN KEY (`idMarca`) REFERENCES `marca` (`idMarca`),
  CONSTRAINT `FK_produto_tipo` FOREIGN KEY (`idTipo`) REFERENCES `tipo` (`idTipo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela meubanco.produto: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` (`idProduto`, `idMarca`, `idTipo`, `idFornecedor`, `estoqueMinimo`, `estoqueAtual`, `nome`, `preco`) VALUES
	(6, 3, 1, 1, 5, 20, 'sss', 444),
	(7, 1, 1, 1, 5, 80, 'Fone', 500);
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;


-- Copiando estrutura para tabela meubanco.tipo
CREATE TABLE IF NOT EXISTS `tipo` (
  `idTipo` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idTipo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela meubanco.tipo: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `tipo` DISABLE KEYS */;
INSERT INTO `tipo` (`idTipo`, `nome`) VALUES
	(1, 'Processador');
/*!40000 ALTER TABLE `tipo` ENABLE KEYS */;


-- Copiando estrutura para tabela meubanco.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL DEFAULT '0',
  `login` varchar(50) NOT NULL DEFAULT '0',
  `senha` varchar(50) NOT NULL DEFAULT '0',
  `tipoUsuario` int(11) NOT NULL DEFAULT '0',
  `dataNascimento` date NOT NULL,
  `cpf` int(11) NOT NULL DEFAULT '0',
  `cep` int(8) NOT NULL DEFAULT '0',
  `cidade` varchar(50) NOT NULL DEFAULT '0',
  `rua` varchar(50) NOT NULL DEFAULT '0',
  `bairro` varchar(50) NOT NULL DEFAULT '0',
  `numeroCasa` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela meubanco.usuario: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`idUsuario`, `nome`, `login`, `senha`, `tipoUsuario`, `dataNascimento`, `cpf`, `cep`, `cidade`, `rua`, `bairro`, `numeroCasa`) VALUES
	(11, 'Babinski\r\n', 'a', 'a', 1, '2016-10-02', 1, 1, 'a', 'a', 'a', 199),
	(12, 's', 'a', '1', 0, '2016-10-07', 123, 123, 'a', 'a', 'a', 199);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


-- Copiando estrutura para tabela meubanco.venda
CREATE TABLE IF NOT EXISTS `venda` (
  `idVenda` int(11) NOT NULL AUTO_INCREMENT,
  `dataVenda` varchar(50) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`idVenda`),
  KEY `FK_venda_usuario` (`idUsuario`),
  CONSTRAINT `FK_venda_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Copiando dados para a tabela meubanco.venda: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
