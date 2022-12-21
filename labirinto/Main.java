// 11/12/2022
// Davi Ribeiro de Paula
// Solucionar um labirinto utilizando recursividade

package labirinto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "src/mapas/des2_mapa2.txt "; // Caminho do mapa a ser testado
        File mapa = new File(path);
        FileReader leitor = new FileReader(mapa);
        BufferedReader buffer = new BufferedReader(leitor);
        String linha;
        int linhas = 0, colunas = 0;

        // Percorre o arquivo a primeira vez, para definir o tamanho da matriz
        while ((linha = buffer.readLine()) != null){
            linhas++;
            colunas = linha.length();
        }
        buffer.close();
        leitor.close();

        char [] [] labirinto = new char [linhas] [colunas];
        buffer = new BufferedReader(leitor = new FileReader(mapa));
        int iStart = 0, jStart = 0;

        // Percorre o arquivo novamente, definindo a posição de cada elemento na matriz
        for (int i = 0; i < labirinto.length; i++){
            linha = buffer.readLine();
            for (int j = 0; j < labirinto[0].length; j++){
                labirinto[i][j] = linha.charAt(j);
                if (labirinto[i][j] == 'S' || labirinto[i][j] == 's'){
                    iStart = i;
                    jStart = j;
                }
                if (labirinto[i][j] == 'E' || labirinto[i][j] == 'e'){
                    labirinto[i][j] = 'E';  // Exclui a possibilidade de caractere minúsculo
                }
                if (labirinto[i][j] == 'X' || labirinto[i][j] == 'x'){
                    labirinto[i][j] = 'X';
                }
            }
        }

        buffer.close();
        leitor.close();

        // Chama a função que resolve o labirinto
        resolveLabirinto(labirinto, iStart, jStart);

        // Labirinto resolvido, chama a função que retorna a string do caminho percorrido
        String caminho = caminhoLabirinto(labirinto, iStart, jStart);
        System.out.printf("\nO caminho percorrido neste labirinto é: %s", caminho);

    }

    // A função a seguir verifica se a posição atual está dentro dos limites possíveis,
    // para evitar o erro de ArrayIndexOutOfBounds
    public static boolean verificaPosicao (char [] [] mapa, int x, int y) {
        if (x >= 0 && y >= 0 && x < mapa.length && y < mapa[x].length)
            return true;
        else return false;
    }

    // A função a seguir irá resolver o labirinto, testando cada posição [x] [y] e alterando seu
    // valor a cada caminho percorrido ou sem saída
    public static boolean resolveLabirinto(char[][] mapa, int xAtual, int yAtual) {
        if (verificaPosicao(mapa, xAtual, yAtual)) {
            char posAtual = mapa[xAtual][yAtual];
            // Passos base
            if (posAtual == 'X' || posAtual == '#' || posAtual == '=')
                return false;
            if (posAtual == 'E')
                return true;

            // Define '=' para cada posição percorrida
            mapa[xAtual][yAtual] = '=';

            // Testa os caminhos possíveis: baixo, cima, direita e esquerda
            if (resolveLabirinto(mapa, xAtual + 1, yAtual))
                return true;
            if (resolveLabirinto(mapa, xAtual - 1, yAtual))
                return true;
            if (resolveLabirinto(mapa, xAtual, yAtual + 1))
                return true;
            if (resolveLabirinto(mapa, xAtual, yAtual - 1))
                return true;

            // Caso não encontre nenhum caminho, retrocede nas chamadas definindo
            // cada posição sem saída como '#', até encontrar um novo caminho possível
            mapa[xAtual][yAtual] = '#';
            return false;
        }
        return false;
    }

    // A função a seguir irá apenas percorrer o labirinto, concatenando em cada
    // chamada um caractere de acordo com qual caminho foi feito
    public static String caminhoLabirinto(char[][] mapa, int xAtual, int yAtual) {
        if (verificaPosicao(mapa, xAtual, yAtual)) {
            if (mapa[xAtual][yAtual] == 'E') {
                return "";
            }
            if (xAtual + 1 < mapa.length) {
                if (mapa[xAtual + 1][yAtual] == '=' || mapa[xAtual + 1][yAtual] == 'E') {
                    mapa[xAtual][yAtual] = '0';
                    return "B" + caminhoLabirinto(mapa, xAtual + 1, yAtual);
                }
            }
            if (xAtual - 1 >= 0) {
                if (mapa[xAtual - 1][yAtual] == '=' || mapa[xAtual - 1][yAtual] == 'E') {
                    mapa[xAtual][yAtual] = '0';
                    return "C" + caminhoLabirinto(mapa, xAtual - 1, yAtual);
                }
            }
            if (yAtual + 1 < mapa[0].length) {
                if (mapa[xAtual][yAtual + 1] == '=' || mapa[xAtual][yAtual + 1] == 'E') {
                    mapa[xAtual][yAtual] = '0';
                    return "D" + caminhoLabirinto(mapa, xAtual, yAtual + 1);
                }
            }
            if (yAtual - 1 >= 0) {
                if (mapa[xAtual][yAtual - 1] == '=' || mapa[xAtual][yAtual - 1] == 'E') {
                    mapa[xAtual][yAtual] = '0';
                    return "E" + caminhoLabirinto(mapa, xAtual, yAtual - 1);
                }
            }
        }
        return "Não existe um caminho até a saída!";
    }

}