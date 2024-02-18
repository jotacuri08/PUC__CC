int array[MAXTAM + 1];
int primeiro, ultimo;

void start(){
    primeiro = ultimo = 0;
}

void inserir(int x){
    if(((ultimo + 1) % (MAXTAM + 1)) == primeiro){
        exit(1);
    }

    array[ultimo] = x;
    ultimo = (ultimo + 1) % (MAXTAM + 1);
}

int remover(){
    if(primeiro == ultimo){
        exit(1);
    }

    int removido = array[primeiro];
    primeiro = (primeiro + 1) % MAXTAM;
}

