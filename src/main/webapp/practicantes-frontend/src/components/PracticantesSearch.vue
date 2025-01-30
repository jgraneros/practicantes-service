<template>
    <div>
        <h1>Buscar por DNI</h1>
        <form @submit.prevent="searchPracticante">
            <label for="dni">DNI: </label>
            <input type="text"
             id="dni"
             v-model="dni"
             placeholder="Ingrese el dni"
             required
            />

            <button type="submit">Buscar</button>
        </form>

        <div v-if="loading">cargando...</div>
        <div v-if="error" class="error">{{error}}</div>

        <div v-if="practicante">
            <h2>Resultado</h2>
            <table>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Cuotas</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>{{ practicante.nombre }}</td>
                        <td>{{ practicante.apellido }}</td>
                        <td>
                            <button @click="showCuotasModal = true">Ver cuotas</button>
                        </td>
                    </tr>
                </tbody>
            </table>

            <dialog v-if="showCuotasModal" open class="modal">
                <h3>Cuotas de {{ practicante?.nombre }} {{ practicante?.apellido }}</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Mes</th>
                        </tr>
                    </thead>
                    <tbody>

                        <tr v-for="(cuota, index) in practicante?.cuotas" :key="index">
                            <td>{{ cuota.fecha }}</td>
                            <td>{{ cuota.estado }}</td>
                            <td>{{ cuota.mes }}</td>
                        </tr>
                    </tbody>
                </table>
                <button @click="showCuotasModal = false">Cerrar</button>
            </dialog>
        </div>
    </div>
</template>

<script setup lang="ts">

    import { ref } from 'vue';
    import axios from 'axios';

    interface Cuota {
        fecha: String;
        estado: String;
        mes: String;
    }

    interface Practicante {
        nombre: String;
        apellido: String;
        cuotas: Cuota[];
    }



    const token = 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJZdUdfdXpIY0tnV1FCb1pZV1RPU0RuVTZLeTZ0QmlSNlRmdVhJQi1QQ0tVIn0.eyJleHAiOjE3MzgyNTg2OTgsImlhdCI6MTczODI1ODA5OCwianRpIjoiZTc3ODQ4YWQtMzJkOC00YzkxLWI1OTAtZGQ2ZTkwMGI4N2RmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDozMjc3MC9yZWFsbXMvcXVhcmt1cyIsInR5cCI6IkJlYXJlciIsImF6cCI6InF1YXJrdXMtYXBwIiwic2lkIjoiZmFkNjUwZjMtMDBkZS00ZmYwLWIwMDItMWVhMzU0NjJkOTU3Iiwic2NvcGUiOiJtaWNyb3Byb2ZpbGUtand0IiwidXBuIjoianVhbiIsImdyb3VwcyI6WyJpbnN0cnVjdG9yIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLXF1YXJrdXMiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfQ.wX1kr-bn1y4piSRBhwqZDJJP27n2hzjNTAMnraWc6AggVOtzZXgYI5-yGnLZc_E1wdvsPA5voVu6dONHmGCLjhLP1JZ49EnXGW6bBQg-cdbOUCXCUtVUMQFF2Zyr2hN4chQk-PIvkGxo2Sil2Wr5aP9-SaFbKWbyqLuMVjpQvwUsq5WH_9XZfFAXnIx-8Lbu8QElaOy1RD40f3HA4xNTClBahyPEDl39LouIMSah_Zl9gjOBpeWBQdlDqAti-r0DtgvgovNdzutvroEXluyQlSHnoh3hzQ7ITlcKrZ-qQSRyGHHKroDVDo93WUaUvsxsGz4h6CjrEIXxJVqkA87nbw';


    const dni = ref<string>('')
    const practicante = ref<Practicante  | null>(null);
    const loading = ref<boolean>(false);
    const error = ref<string>('');
    const showCuotasModal = ref<boolean>(false);

    const searchPracticante = async (): Promise<void> => {

        if (!dni.value) {
            error.value = "Por favor ingrese un dni valido"
            return;
        }

        loading.value = true;
        error.value = '';
        practicante.value = null;

        try {
            const response = await axios.get('http://localhost:8081/practicantes/v1', {
                params: {dni: dni.value},
                headers: {
                Authorization: `Bearer ${token}`, // Envía el token en el header
                },
            });

            practicante.value = response.data;
        } catch (err) {
            error.value = "Error"
            console.log(err)
        } finally {
            loading.value = false;
        }
    }

</script>

<style scoped>
    .error {
    color: red;
    }


    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 1rem;
    }

    th, td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
      }

      th {
        background-color: #f4f4f4;
      }

      button {
        cursor: pointer;
      }

      .modal-background {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5); /* Fondo semitransparente */
        z-index: 1000; /* Asegura que esté por encima de todo */
      }
      
      .modal {
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%); /* Centra el modal */
        background-color: white;
        padding: 1rem;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        z-index: 1001; /* Asegura que esté por encima del fondo */
      }
      
      .modal h3 {
        margin-top: 0;
      }
      
      .modal button {
        margin-top: 1rem;
      }
</style>