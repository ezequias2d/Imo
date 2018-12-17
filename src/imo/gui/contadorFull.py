# coding: utf-8
import os
linhas = 0
dirP = os.getcwd() + '/'
saveList = []
formatF = ''
run = True

def clear():
    try:
        lines = os.get_terminal_size().lines
    except AttributeError:
        lines = 130
    print("\n" * lines)

def listar(dir_pri, dir1,soma):
    global linhas
    if os.path.isdir(dir_pri + dir1):
        for arquivo in os.listdir(dir_pri + dir1):
            if os.path.isdir(dir_pri+dir1+arquivo):
                listar(dir_pri, dir1+arquivo+'/',soma)
            else:
                arq = dir_pri+dir1+arquivo
                linhas += soma(arq)
    else:
        arq = dir_pri+diretorio
        linhas += soma(arq)

def palavras(dir_pri, dir1):
    global linhas
    if os.path.isdir(dir_pri + dir1):
        for arquivo in os.listdir(dir_pri + dir1):
            if os.path.isdir(dir_pri+dir1+arquivo):
                listar(dir_pri, dir1+arquivo+'/')
            else:
                arq = dir_pri+dir1+arquivo
                linhas += somaC(arq)
    else:
        arq = dir_pri+diretorio
        linhas += somaC(arq)

def somaLinhas(fileRead):
    global dirP, formatF,saveList
    if fileRead.lower().endswith(formatF):
        op = open(fileRead,'r')
        linhasL = op.readlines()
        #print(fileRead.replace(dirP,'') + ':' + str(len(linhasL)))
        saveList.append(fileRead.replace(dirP,'') + ':' + str(len(linhasL)))
        op.close()
        return len(linhasL)
    return 0

def somaPalavras(fileRead):
    global dirP, formatF,saveList
    exep = [':',' ', '','(',')',',']
    saidaPalavras = 0
    if fileRead.lower().endswith(formatF):
        op = open(fileRead,'r')
        linhasL = op.readlines()
        for i in linhasL:
            for j in exep:
                k = i.replace(j,' ')
            li = k.split(' ')
            saidaPalavras += len(li)

        #print(fileRead.replace(dirP,'') + ':' + str(len(linhasL)))
        saveList.append(fileRead.replace(dirP,'') + ':' + str(saidaPalavras))
        op.close()
        return saidaPalavras
    return 0

def somaCaracteres(fileRead):
    global dirP, formatF,saveList
    saidaPalavras = 0
    if fileRead.lower().endswith(formatF):
        op = open(fileRead,'r')
        linhasL = op.readlines()
        for i in linhasL:
            saidaPalavras += len(i)

        #print(fileRead.replace(dirP,'') + ':' + str(len(linhasL)))
        saveList.append(fileRead.replace(dirP,'') + ':' + str(saidaPalavras))
        op.close()
        return saidaPalavras
    return 0


clear()
while run:
    linhas = 0
    print("Insira um comando:")
    print('1-Contar linhas e mostrar na tela')
    print('2-Contar linhas e salvar em k.txt')
    print('3-Contar palavras e mostrar na tela')
    print('4-Contar palavras e salvar em k.txt')
    print('e-Sair' + os.linesep)

    inp = raw_input('>')
    if inp == '1':
        clear()
        print('Contador de linhas de arquivos!')
        print('Insira a seguir o formato do arquivo')
        print('Exemplos:')
        formatos = ['.py','.c','.cpp','.csharp','.java']
        for i in formatos:
            print(i)
        formatF = raw_input('Insira o formato:')
        listar(dirP,'',somaLinhas)
        saveList.append('')
        saveList.append('Linhas:' + str(linhas))

        #print(os.linesep + 'Linhas:' + str(linhas) + os.linesep)
        clear()
        for x in saveList:
            print(x)
        print
    elif inp == '2':
        clear()
        print('Contador de linhas de arquivos!')
        print('Insira a seguir o formato do arquivo')
        print('Exemplos:')
        formatos = ['.py','.c','.cpp','.csharp','.java']
        for i in formatos:
            print(i)
        formatF = raw_input('Insira o formato:')
        listar(dirP,'',somaLinhas)
        saveList.append('Linhas:' + str(linhas))
        clear()
        op = open('k.txt','w')
        for i in saveList:
            op.writelines(i+os.linesep)
        op.close()
    elif inp == '3':
        clear()
        print('Contador de palavras dos arquivos!')
        print('Insira a seguir o formato do arquivo')
        print('Exemplos:')
        formatos = ['.py','.c','.cpp','.csharp','.java']
        for i in formatos:
            print(i)
        formatF = raw_input('Insira o formato:')
        listar(dirP,'',somaPalavras)
        saveList.append('Palavras:' + str(linhas))
        clear()
        for i in saveList:
            print(i)
    elif inp == '4':
        clear()
        print('Contador de palavras dos arquivos!')
        print('Insira a seguir o formato do arquivo')
        print('Exemplos:')
        formatos = ['.py','.c','.cpp','.csharp','.java']
        for i in formatos:
            print(i)
        formatF = raw_input('Insira o formato:')
        listar(dirP,'',somaPalavras)
        saveList.append('Palavras:' + str(linhas))
        clear()
        op = open('k.txt','w')
        for i in saveList:
            op.writelines(i+os.linesep)
        op.close()
    elif inp == '5':
        clear()
        print('Contador de caracteres dos arquivos!')
        print('Insira a seguir o formato do arquivo')
        print('Exemplos:')
        formatos = ['.py','.c','.cpp','.csharp','.java']
        for i in formatos:
            print(i)
        formatF = raw_input('Insira o formato:')
        listar(dirP,'',somaCaracteres)
        saveList.append('Palavras:' + str(linhas))
        clear()
        for i in saveList:
            print(i)
    elif inp == '6':
        clear()
        print('Contador de caracteres dos arquivos!')
        print('Insira a seguir o formato do arquivo')
        print('Exemplos:')
        formatos = ['.py','.c','.cpp','.csharp','.java']
        for i in formatos:
            print(i)
        formatF = raw_input('Insira o formato:')
        listar(dirP,'',somaCaracteres)
        saveList.append('Palavras:' + str(linhas))
        clear()
        op = open('k.txt','w')
        for i in saveList:
            op.writelines(i+os.linesep)
        op.close()
    elif inp == 'e':
        run = False
