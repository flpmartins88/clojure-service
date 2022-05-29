# Serviço de exemplo em Clojure

Serviço usando coisas que fui aprendendo

Versão inicial baseada [nesse tutorial](http://pedestal.io/guides/pedestal-with-component)

## Passos iniciais para executar o projeto:

### Configurar o local-dev do Datomic:

Você precisa obter os dados de acesso para o repositório do dev-local do Datomic [nesse site](https://docs.datomic.com/cloud/dev-local.html)

Após o cadastro, você vai receber os dados de acesso por email, a partir daí é só configurar duas variáveis de ambiente:
- LEIN_USERNAME: Geralmente seu email de cadastro, enviado por email
- LEIN_PASSWORD: Token enviado por email

### Kafka
O arquivo do docker-compose tem tudo o que é preciso do kafka para rodar

    docker-compose up -d kafka-broker

Para criar os tópicos:

    docker-compose up kafka-setup

## Testes

Passos necessários para rodar os testes do projeto é necessário ter algumas coisas configuradas

### Datomic
É preciso ter configurado o local-dev

### Kafka
Os testes ainda dependem do kafka real para execução

## Libs

- [Components](https://github.com/stuartsierra/component)
- [Pedestal](http://pedestal.io/guides)
- [Schema](https://github.com/plumatic/schema)
- [Datomic](https://datomic.com) (usando local-dev)
- [Kafka](https://kafka.apache.org)
- [Matcher Combinators](https://github.com/nubank/matcher-combinators)

# Futuro

- Implementar um mock do kafka
- Melhorar a integração com o Datomic
- Melhorar a estrutura do projeto
- Achar um Datomic em Docker????
- Fazer o projeto subir (hoje só sobe com o REPL)
- Melhorar os testes
- Separar os testes unitários dos testes de integração
- Melhorar a validação de esquema pelo http para dar BAD REQUEST automaticamente
- Consumir eventos do Kafka
- Descobrir como configurar o Github para rodar os testes do projeto (hoje falha por não conseguir acessar o maven da Cognitec)
