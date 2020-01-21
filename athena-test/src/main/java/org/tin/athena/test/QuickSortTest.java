package org.tin.athena.test;


/**
 * 快排算法： 挖坑填数
 * 1、循环游标，i=L（从左往右遍历）, j=R（从右往左遍历） ，确定基准数，例如 arr[L]
 * 2、挖坑：temp=arr[L]，j--从后面查找，找出比基准数小的，填到arr[L]位置，并挖出新坑arr[j]
 * 3、i++从左往右找出比基准数大的，填到上一个坑arr[j]
 * 4、循环步骤2，直到 i>=j，将基准数填到arr[i]
 *
 * 基准数优化：
 * 1、固定基准数
 * 2、随机基准数
 * 3、三数取中值（一般是取最左、中间、最右三数）
 *
 * */
public class QuickSortTest {

    private static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void selectMid(int[] arr, int left, int right){
        int mid = left + (right - left) / 2;

        //保证左边较小
        if(arr[left] > arr[right]){
            swap(arr, left, right);
        }

        if(arr[mid] > arr[right]){
            swap(arr, mid, right);
        }

        //保证左边为中值
        if(arr[mid] > arr[left]){
            swap(arr, mid, left);
        }

    }

    //返回本轮结束时的位置
    private static int partition(int[] arr, int left, int right){
        //固定基准数
        int temp = arr[left];

        //三数取中值
        selectMid(arr, left, right);
        temp = arr[left];

        while (right > left){
            //从后往前找比基准数小的数
            while (temp <= arr[right] && left < right){
                right--;
            }

            //找到小数，或者没找到，都要将前一个坑填上
            if(left < right){
                arr[left] = arr[right];
                left++;
            }

            //从前往后找比基准值大的数
            while (temp >= arr[left] && left < right){
                left++;
            }

            //找到大数，或者没找到，都要将前一个坑填上
            if(left < right){
                arr[right] = arr[left];
                right--;
            }

        }

        arr[left] = temp;

        return left;
    }

    private static void sort(int[] arr, int left, int right){
        if(arr==null || arr.length<=1 || left>=right){
            return;
        }

        int mid = partition(arr, left, right);
        sort(arr, left, mid);
        sort(arr, mid+1, right);
    }

    public static void main(String[] args) {
        int[] arr = {7, 5, 0, 2, 8, 7, 9, 4};
        sort(arr, 0, arr.length-1);

        for(int i : arr){
            System.out.print(i + " ");
        }

    }

}
