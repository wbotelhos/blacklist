# R7 Exercise | Blacklist

Implementação sem ajuda de bibliotecas extras de um serviço HTTP censurador de comentários baseado em blacklist.

## Author

Washington Botelho [[Blog](http://wbotelhos.com.br) | [Twitter](http://twitter.com/#!/wbotelhos)]

## Tutorial

1. Adicione o projeto cliente ```blacklist-client``` e o projeto server ```blacklist-server``` no Tomcat;
2. Crie o schema ```r7``` e o usuário ```r7``` com senha igual à ```rrrrrrr``` com permissão total nesse schema;
3. Execute a classe ```PopulateHelper``` do projeto client para que o banco de dados seja alimentado;
4. Inicie o Tomcat e execute o projeto cliente pela URL ```localhost:8080/blacklist-client```;
5. Será apresentado uma área para que o comentário seja adicionado e logo abaixo os comentários já gravados no banco;
6. Digite algum comentário e clique no botão "Comentar" e seu comentário será adicionado com as devidas censuras;
7. Para publicar o projeto online na Amazon basta executar o script ```deploy.sh```* de cada um dos projetos;
8. O client ficará no endereço ```[FILTERED]/blacklist-client``` e o server ficará em ```[FILTERED]/blacklist-server```;
9. Se o banco online ainda não existir ou quiser alimentá-lo com os dados padrão execute o script ```sql.sh```* do projeto client;
10. Caso prefira editar os valores manualmente do banco online, acesse o server ```[FILTERED]``` com o usuário ```r7``` e senha ```rrrrrrr```.

	&#42; Para executar estes scripts é necessário a chave .ssh do usuario da Amazon.

## How It Works?

* O usuário digita um comentário no qual este é submetido para um servidor HTTP que o recebe e executa um filtro.
* Este filtro carrega do banco de dados uma lista de palavras proibidas que é utilizada como consulta no comentário enviado.
* Caso alguma destas palavras seja encontrada, a mesma é substituida por xxxx.
* Para evitar que o usuário burle o sistema, ainda há uma ferramenta para achar palavras "codificadas".
* Digamos que a palavra proibida seja "arma", logo o filtro irá censurar as seguintes palavras: arma, @arma, arrmmaRr, àAãRrrMM@@á etc...  

## Architecture

Foi utilizado o MVC extendido que inclui a camada de negócio (Business) e a camada de acesso ao banco (DAO).

Fluxo: View (JSP) -> Controller (Servlet) -> Model/Business -> DAO -> MySQL

## Used Tools

- Ajax: todas as ações são via ajax executado manualmente;
- ANT: ferramenta utilizada para fazer o build do projeto;
- Coverage: biblioteca para gerar relatórios durante o build mostrando a abrangência dos testes;
- CSS: o sistema possui um design simples só para organizar os dados.
- DbUnit: usado para os testes de integração.
- EL: utilizado para pegar o contexto corrente;
- Java: linguagem utilizada no lado do servidor;
- JavaScript: utilizado para fazer as ações no lado do cliente;
- jIntegrity: framework que desenvolvi e utilizo para ajudar nos testes;
- JSON: formato adotado para a comunição entre o cliente e o servidor;
- JSP: utilizado por suportar a EL citada anteriormente;
- JUnit: utilizado para criar os testes;
- Minify: feature utilizada para comprimir os arquivos CSS e JS;
- Mockito: biblioteca utilizada para os testes funcionais;
- MySQL: banco de dados adotado;
- Servlet: utilizado no processo server side para a manipulação HTTP;
- SH: script utilizado para deploy e alimentação do banco;
- Test Report: feature utilizada para gerar um relatório durante o build mostrando o resultado dos testes.
- Tomcat: servidor utilizado para rodar a aplicação.
