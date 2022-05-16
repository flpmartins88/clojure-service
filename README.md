# Serviço de exemplo em Clojure

Serviço usando coisas que fui aprendendo

Versão inicial baseada [nesse tutorial](http://pedestal.io/guides/pedestal-with-component)

## Testes
Para rodar os testes você precisa obter os dados de acesso para o repositório do dev-local do Datomic [nesse site](https://docs.datomic.com/cloud/dev-local.html)

Após o cadastro, você vai receber os dados de acesso por email, a partir daí é só configurar duas variáveis de ambiente:
- LEIN_USERNAME: Geralmente seu email de cadastro, enviado por email
- LEIN_PASSWORD: Token enviado por email

## Libs

- [Components](https://github.com/stuartsierra/component)
- [Pedestal](http://pedestal.io/guides)
- [Schema](https://github.com/plumatic/schema)
- Datomic (soon)
- [Matcher Combinators](https://github.com/nubank/matcher-combinators)
