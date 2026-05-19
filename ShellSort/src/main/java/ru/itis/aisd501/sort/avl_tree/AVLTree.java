package ru.itis.aisd501.sort.avl_tree;

public class AVLTree {

    private class Node { //сам узел
        int value; //его значение
        Node left;
        Node right; //его потомки
        int height; //высота узла, максимальное расстояние до последнего потомка

        Node(int value) {
            this.value = value; //значение узла
            this.height = 1; //смотрим снизу вверх, новый узел всегда будет с высотой 1
        }
    }

    private Node root; //корень, самое первое значение
    private int operationCount; //переменная для замера количества операций

    public int getOperationCount() {
        return operationCount;
    }

    public void resetOperationCount() {
        this.operationCount = 0;
    }

    public AVLTree() { //создание дерева с нуля
        this.root = null; //пока узлов нет
        this.operationCount = 0;
    }

    private int height(Node node) { //высота узла
        return node == null ? 0 : node.height; //если узел пустой, то 0
    }

    private void updateHeight(Node node) { //обновление высоты узла
        node.height = Math.max(height(node.left), height(node.right)) + 1; //находим большую высоту у потомка и добавляем 1
    }

    private int balanceFactor(Node node) { //ищем разность между левым и правым потомком
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) { //поворот направо
        Node x = y.left; //левый потомок узла, станет новым корнем
        Node T2 = x.right; //поддерево текущего будущего корня

        x.right = y; //текущий корень стал потомком справа
        y.left = T2; //так как раньше оно было в левом отсеке относительно бывшего корня, оно переносится туда же

        updateHeight(y); //обновляем высоты узлов
        updateHeight(x);
        return x; //возвращаем новый корень
    }

    private Node rotateLeft(Node x) { //то же самое, но в левую сторону
        Node y = x.right; //будущий корень
        Node T2 = y.left; //текущее поддерево будущего корня

        y.left = x; //сдвигаем бывший корень влево
        x.right = T2; //передаём ему бывшее поддерево нового корня

        updateHeight(x); //обновляем высоты
        updateHeight(y);
        return y; //возвращаем новый корень
    }

    private Node balance(Node node) { //балансировка, которая решает нужно ли повернуть дерево и в какую сторону
        if (node == null) return null;

        updateHeight(node); //актуализируем высоту узла
        int bf = balanceFactor(node); //находим разность в высотах

        if (bf > 1) { //если bf > 1, значит левая часть имеет сильно большую высоту, надо поворачивать
            if (balanceFactor(node.left) < 0) { //если у левого потомка больше потомков справа, то тоже поворачиваем
                node.left = rotateLeft(node.left); //причем сначала левый поворот, а затем правый
            }
            return rotateRight(node); //в любом случае делаем правый поворот
        }
        if (bf < -1) { //то же самое относительно правого перекоса
            if (balanceFactor(node.right) > 0) { //если вдруг получили случай RL (т.е. потомок имеет левый перекос) поворачиваем вправо
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node); //в любом случае в конце поворачиваем влево
        }
        return node; //возвращаем повёрнутое сбалансированное дерево
    }

    public void insert(int value) { //публичный метод вставки
        root = insertRec(root, value);
    }

    private Node insertRec(Node node, int value) { //приватный метод рекурсивной вставки
        operationCount++; //служебный оператор для счёта операций
        if (node == null) { //базовый случай рекурсии, дошли до пустого узла
            return new Node(value); //создаём новый узел
        }

        if (value < node.value) { //если новое значение меньше того, что в текущем узле
            node.left = insertRec(node.left, value); //вставляем его слева
        } else if (value > node.value) { //если больше, то справа
            node.right = insertRec(node.right, value);
        } else {
            return node; //иначе повторяющиеся значения не вставляем вообще
        }

        return balance(node); //возвращаем сбалансированный узел
    }

    private Node findMin(Node node) { //нахождение минимального, которое пригодится при удалении
        Node current = node;
        while (current.left != null) { //идём до конца влево
            operationCount++; //служебный оператор
            current = current.left; //спускаемся на уровень ниже
        }
        return current; //возвращаем минимальный узел
    }

    public void delete(int value) { //публичный метод для удаления
        root = deleteRec(root, value);
    }

    private Node deleteRec(Node node, int value) { //приватный метод рекурсивного удаления
        if (node == null) {  //базовый случай рекурсии - элемент не найден
            operationCount++; //служебный оператор
            return null;
        }
        operationCount++; //служебный оператор
        if (value < node.value) { //если искомое значение меньше узла, то идём влево
            node.left = deleteRec(node.left, value); //ищем дальше
        } else if (value > node.value) { //если больше, то идём вправо
            node.right = deleteRec(node.right, value); //рекурсивно ищем дальше
        } else { //значения совпали, работаем с текущим узлом
            if (node.left == null || node.right == null) { //если этот узел имеет одного или ни одного потомка
                Node temp = (node.left != null) ? node.left : node.right; //просто заменяем текущий узел потомком
                node = temp;
            } else { //если есть оба потомка, надо решить, кто встанет вместо найденного узла
                Node temp = findMin(node.right); //находим минимальный узел в правом поддереве
                /*
                Всё потому, что он будет наименьшим для всего правого поддерева, но
                в то же время наибольшим для всего левого поддерева, что идеально подходит
                под описание родительского узла
                 */
                node.value = temp.value; //заменяем значение узла на значение минимального в правом поддереве
                node.right = deleteRec(node.right, temp.value); //удаляем из правого поддерева перенесённое значение
            }
        }
        if (node == null) return null; //если узел был удален и стал null, то балансировать и не надо
        return balance(node); //возвращаем сбалансированное дерево
    }

    public boolean search(int value) { //публичный метод поиска
        return searchRec(root, value);
    }

    private boolean searchRec(Node node, int value) { //приватный метод рекурсивного поиска
        operationCount++; //служебный оператор
        if (node == null) return false; //возвращаем false, если передан пустой узел
        if (value == node.value) return true; //если значение совпадает со значением переданного узла, возвращаем true
        if (value < node.value) return searchRec(node.left, value); //если оно меньше значения в узле, рекурсивно ищем слева
        return searchRec(node.right, value); //иначе рекурсивно ищем справа
    }
}
