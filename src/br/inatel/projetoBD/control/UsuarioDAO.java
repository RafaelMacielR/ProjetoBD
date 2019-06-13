/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inatel.projetoBD.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author macie
 */
public class UsuarioDAO {
        // objeto responsavel pela conexao com o servidor do banco de dados
    Connection con;
    // objeto responsavel por preparar as consultas DINÂMICAS
    PreparedStatement pst;
    // objeto responsavel por executar as consultas ESTÁTICAS
    Statement st;
    // objeto responsavel por referenciar a tabela resultante da busca
    ResultSet rs;
    
    String database = "bancos";
    String url = "jdbc:mysql://localhost:3306/" + database + "?useTimezone=true&serverTimezone=UTC&useSSL=false";
    String user = "root";
    String password = "manodoceu";

    boolean sucesso = false;

    public void connectToDB() {
        try {

            con = DriverManager.getConnection(url, user, password);

            System.out.println("Conexão feita com sucesso!");

        } catch (SQLException ex) {

            System.out.println("Erro: " + ex.getMessage());

        }
    }
    public boolean inserirUsuario(Usuario novoUsuario) 
    {
        connectToDB();

        String sql = "  INSERT INTO usuario (password, nome) values (?,?)";// os valores da interrogação serão setados nas linhas 62 e 63

        try {

            pst = con.prepareStatement(sql);
            // valores que serão passados para consulta no DB
            pst.setString(1, novoUsuario.getSenha());
            pst.setString(2, novoUsuario.getNome());
            pst.execute();

            sucesso = true;

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }

        return sucesso;
    }

    public ArrayList<Usuario> buscarUsuariosSemFilto() 
    {
        
        ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();

        connectToDB();

        String sql = "SELECT * FROM usuario";

        try {

            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            
            System.out.println("Lista de Usuários: ");
            while(rs.next())
            {
               Usuario usuarioTemp = new Usuario(rs.getString("senha"),rs.getString("login"));
                System.out.println("Nome: "+usuarioTemp.getNome());
                System.out.println("------------------------------");    
               
                listaDeUsuarios.add(usuarioTemp); // coloca os usuarios do banco de dados no array list na aplicação java
            }
            
            
            sucesso = true;

        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            sucesso = false;
        } finally {
            try {
                con.close();
                pst.close();
            } catch (SQLException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }

        return listaDeUsuarios;
    }
}
