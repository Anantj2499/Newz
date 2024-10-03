## Setup Instructions

### Create `apikeys.properties` File

1. Create the `apikeys.properties` file and add the `API_KEY`:
    ```sh
    echo "API_KEY=<MY_API_KEY>" > apikeys.properties
    ```

2. Add the `apikeys.properties` file to `.gitignore`:
    ```sh
    echo "apikeys.properties" >> .gitignore
    ```

3. Verify the changes:
    ```sh
    cat .gitignore
    ```