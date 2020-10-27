# Dynamic Programming

### When to Use
- If you noticed a problem can be solved recursively, you can also use Dynamic Programming.
    - Instead of calculating results every time (as in recursion), you can save a HashMap of results by using some unique key for that scenario (often the recursion-specific parameters).
    - Then, before the recursive step, check if the result has already been calculated.
        - If not, perform the recursive step, but instead of returning, put the result into the HashMap.
        - Lastly, return the HashMap lookup for the current scenario (so if you've calculated it, just return the lookup. if you haven't, calculate it, store it, and look it up)