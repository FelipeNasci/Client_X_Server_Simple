# Client_X_Server_Simple

Implementar um Servidor Web a partir de dois arquivos base. Na classe Servidor deve estar
implementado o Servidor e na classe Conexao o socket que irá comunicar-se com o cliente segundo
o protocolo HTTP. Para verificar o funcionamento do servidor, você poderá escolher como cliente do
seu servidor um Browser (navegador) da sua preferência.

1) suportar o MÁXIMO de códigos de respostas do protocolo HTTP (ver referencia 1 na seção
final deste documento);

O sistema retorna códigos 200 OK | 404 Página não encontrada | 401 Não Autorizado

2) comportar Virtual Hosting;

3) implementar um mecanismo de autenticação de usuários para proteger pastas específicas;

Feito

4) desenvolver um mecanismo de log de acesso;

Feito - Armazena data, endereço do cliente, tipo de requisição recebida e enviada

5) aceitar múltiplas conexões de clientes.

Feito - Pool de Threads
