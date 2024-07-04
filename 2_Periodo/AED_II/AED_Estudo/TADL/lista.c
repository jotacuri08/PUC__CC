#define MAXTAM = 6

int array[MAXTAM];
int n;

void start(){
    n = 0;
}

void inserirInicio(int x){
    if(n >= MAXTAM){
        exit(1);
    }

    for (int i = n; i > 0; i--){
        array[i] = array[i-1];
    }

    array[0] = x;
    n++;
}