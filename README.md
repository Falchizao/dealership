GET (MÉTODO GET PROTOCOLO HTTP)

GET VENDA POR ID:
http://localhost:8085/api/vendas/(ID DA VENDA)

GET ALL VENDAS:
http://localhost:8085/api/vendas

GET ATENDENTE POR ID:
http://localhost:8085/api/atendentes/(ID DO ATENDENTE)

GET ALL ATENTENDES:
http://localhost:8085/api/atendentes

GET CARRO POR ID:
http://localhost:8085/api/carros/(ID DO CARRO)

GET ALL CARROS:
http://localhost:8085/api/carros

GET CLIENTE POR ID:
http://localhost:8085/api/clientes/(ID DO CLIENTE)

GET ALL CLIENTES:
http://localhost:8085/api/clientes

DELETE (MÉTODO DELETE PROTOCOLO HTTP)

DELETE VENDA POR ID:
http://localhost:8085/api/vendas/(ID DA VENDA)

DELETE ATENDENTE POR ID:
http://localhost:8085/api/atendentes/(ID DO ATENDENTE)

DELETE CARRO POR ID:
http://localhost:8085/api/carros/(ID DO CARRO)

DELETE CLIENTE POR ID:
http://localhost:8085/api/clientes/(ID DO CLIENTE)

ATUALIZAR (MÉTODO PUT PROTOCOLO HTTP)

UPDATE ATENDENTE POR ID:
http://localhost:8085/api/atendentes/(ID DO ATENDENTE)
BODY JSON:
{
"nomeAtendente": "teste",
"permissao" : 1
}

UPDATE CARRO POR ID:
http://localhost:8085/api/carros/(ID DO CARRO)
BODY JSON:
{

	"modelo": "sedan",
	"marca": "Tesla",
	"cor" : "azul cromado",
	"ano" : "2020",
	"placa" : "4545234",
	"chassi" : "3535",
	"valor" : 30000.00
}

UPDATE CLIENTE POR ID:
http://localhost:8085/api/clientes/(ID DO CARRO)
BODY JSON:
{
"nomeCliente": "valcirzika",
"cpfCliente": "34770565845",
"rgCliente" : "55667766",
"enderecoCliente" : "Rua ali ohhh",
"telefoneCliente" : "454545454",
"emailCliente" : "valcirzika@gmail.com"
}

UPDATE VENDA POR ID:
http://localhost:8085/api/vendas/(ID DA VENDA)
BODY JSON:
{
"idCarro": 4,
"formaPagamento": "credito",
"idCliente" : 1,
"atendente" : 3
}


REGISTRAR (MÉTODO POST PROTOCOLO HTTP)

REGISTRO NOVO CLIENTE:
http://localhost:8085/api/clientes/registrar
BODY JSON:
{
"nomeCliente": "valcirzika",
"cpfCliente": "34770565845",
"rgCliente" : "55667766",
"enderecoCliente" : "Rua Tamarutaco",
"telefoneCliente" : "454545454",
"emailCliente" : "valcirzika@gmail.com"
}


REGISTRO NOVO CARRO
http://localhost:8085/api/carros/registrar
BODY JSON:
{

	"modelo": "sedan",
	"marca": "Tesla",
	"cor" : "azul cromado",
	"ano" : "2020",
	"placa" : "4545234",
	"chassi" : "3535",
	"valor" : 30000.00,
	"quantity" : 2
}


REGISTRO NOVO ATENDENTE
http://localhost:8085/api/atendentes/registrar
BODY JSON:
{
"nomeAtendente": "teste",
"permissao" : 3
}


REGISTRO NOVA VENDA
http://localhost:8085/api/vendas/registrar
BODY JSON:
{
"idCarro": 4,
"formaPagamento": 1,
"idCliente" : 2,
"atendente" : 4
}



