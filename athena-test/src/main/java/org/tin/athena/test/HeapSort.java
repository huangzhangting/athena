package org.tin.athena.test;


/**
 * 堆排序：
 * 利用堆的特性
 * 0、完全二叉树
 * 1、大顶堆：节点元素 >= 左右子节点
 * 2、小顶堆：节点元素 <= 左右子节点
 *
 * 排序逻辑
 * 1、构建大顶堆，然后将堆顶元素交换到原数组尾部
 * 2、剩余的数据，再次构建大顶堆，然后移走堆顶元素
 *
 * */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = { 50, 10, 90, 30, 70, 40, 80, 60, 20 };

        // 将待排序的序列构建成一个大顶堆
//        for (int i = arr.length / 2; i >= 0; i--){
//            heapAdjust(arr, i, arr.length);
//        }

        System.out.println("排序之前：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();

        // 堆排序
        heapSort(arr);

        System.out.println("排序之后：");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();

    }

    /**
     * 堆排序
     */
    private static void heapSort(int[] arr) {
        // 将待排序的序列构建成一个大顶堆，第一个元素是堆顶，所以从最底层的父节点开始调整
        for (int i = arr.length / 2; i >= 0; i--){
            heapAdjust(arr, i, arr.length);
        }

        // 逐步将每个最大值的根节点与末尾元素交换，并且再调整二叉树，使其成为大顶堆
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i); // 将堆顶记录和当前未经排序子序列的最后一个记录交换

            //因为只交换了第一个元素，所以从idx 0 开始重新调整
            heapAdjust(arr, 0, i); // 交换之后，需要重新检查堆是否符合大顶堆，不符合则要调整
        }
    }

    /**
     * 构建堆的过程
     * @param arr 需要排序的数组
     * @param i 需要构建堆的根节点的序号
     * @param n 数组的长度
     */
    private static void heapAdjust(int[] arr, int i, int n) {
        //4, 9
        //3, 9 lc=7
        //2, 9 lc=5
        //1, 9 lc=3
        //0, 9 lc=1

        int child;
        int father = arr[i];
        while (leftChild(i) < n){
            child = leftChild(i);

            // 如果左子树小于右子树，则需要比较右子树和父节点
            if (child != n - 1 && arr[child] < arr[child + 1]) {
                child++; // 序号增1，指向右子树
            }

            // 如果父节点小于孩子结点，则需要交换
            if (father < arr[child]) {
                arr[i] = arr[child];
            } else {
                break; // 大顶堆结构未被破坏，不需要调整
            }

            //继续遍历有可能变化的子节点
            i = child;

        }

        arr[i] = father;
    }

    // 获取到左孩子结点
    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    // 交换元素位置
    private static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

}

