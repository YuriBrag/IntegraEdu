package yuri.bragine.integraedu;

public class Aluno {
    private String nome;
    private String sobrenome;
    private double nota;
    private int frequencia;
    private String satisfacao;
    private int lancamentosVencidos;
    private String situacaoMatricula;

    public Aluno(String nome, String sobrenome, double nota, int frequencia, String satisfacao, int lancamentosVencidos, String situacaoMatricula) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nota = nota;
        this.frequencia = frequencia;
        this.satisfacao = satisfacao;
        this.lancamentosVencidos = lancamentosVencidos;
        this.situacaoMatricula = situacaoMatricula;
    }

    public String getNome() { return nome; }
    public String getSobrenome() { return sobrenome; }
    public double getNota() { return nota; }
    public int getFrequencia() { return frequencia; }
    public String getSatisfacao() { return satisfacao; }
    public int getLancamentosVencidos() { return lancamentosVencidos; }
    public String getSituacaoMatricula() { return situacaoMatricula; }
}
