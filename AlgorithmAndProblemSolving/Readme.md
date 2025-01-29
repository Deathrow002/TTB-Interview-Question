## Binary Search on an Array of 1,000,000 Numbers

This application performs a binary search to find the index of a specified target number in an array containing integers from 1 to 1,000,000.

### How it Works
1. The array is populated with numbers starting from 1 to 1,000,000.
2. Binary search is performed to find the index of the target number in the array.
3. The search algorithm divides the array into halves, checking the middle element at each step to determine if the target is found.
4. The time taken for the search is measured and displayed in milliseconds.

### Example Search: Finding 900,000
To find the value `900,000` in the array, the application will:
- Search for the value starting from the first element (index 0).
- Use binary search to narrow down the position.
- Once the target value is found, the index of `900,000` will be displayed (which should be `899,999` due to zero-indexing).

### Performance
The binary search works efficiently on an array of 1,000,000 elements, with a search time that is logarithmic (O(log n)) in complexity. The time taken for each search is displayed in the console.

---