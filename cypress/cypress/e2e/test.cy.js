describe("CNPJ page", () => {
  beforeEach(() => {
    cy.on("uncaught:exception", () => {
      return false;
    });

    cy.visit(
      "https://buscatextual.cnpq.br/buscatextual/busca.do?metodo=apresentar"
    );
  });

  it("set text - check/uncheck checkboxes", () => {
    cy.get("#buscarDemais").should("be.visible");
    cy.get("#buscarBrasileiros").should("be.visible");
    cy.get("#buscarEstrangeiros").should("be.visible");
    cy.get("#buscarDoutores").should("be.visible");

    cy.get("#textoBusca").type("Matheus");
    cy.get("#buscarDemais").check().should("be.checked");
    cy.get("#buscarBrasileiros").check().should("be.checked");
    cy.get("#buscarEstrangeiros").uncheck().should("not.be.checked");
    cy.get("#buscarDoutores").uncheck().should("not.be.checked");

    cy.get(".control-bar-wrapper > #botaoBuscaFiltros").should('be.visible').click();

    cy.url().should("include", "/busca.do");

    cy.get("ol li").contains("Matheus").should("exist");
    cy.get("ol li").contains("Matheus").click();

    cy.get('#idbtnabrircurriculo').should('be.visible');
  });
});
