<template>
    <div>
        <h1>Buscar por DNI</h1>
        <form @submit.prevent="searchPracticante">
            <label for="dni">DNI: </label>
            <input type="text" id="dni" v-model="dni" placeholder="Ingrese el dni" required />

            <button type="submit">Buscar</button>
        </form>

        <div v-if="loading">cargando...</div>
        <div v-if="error" class="error">{{ error }}</div>

        <div v-if="practicante">
            <h2>Resultado</h2>
            <table class="content-table">
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
                <table class="content-table">
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



const token = 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyNk5maTRUZGFzb2pmTDRIOFJDMFEwd04wWnNuXzFiR2x5Qkd6NkY5Y0JZIn0.eyJleHAiOjE3Mzg0NDAyNDEsImlhdCI6MTczODQzOTY0MSwianRpIjoiMjRkMjRjODYtMTFiYS00ZWQ2LWIzY2MtZThkOGRmNDU4ZjU2IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDozMjc3MC9yZWFsbXMvcXVhcmt1cyIsInR5cCI6IkJlYXJlciIsImF6cCI6InF1YXJrdXMtYXBwIiwic2lkIjoiN2U4MjAyM2QtYThiYi00ODFiLTlkMzktMDhjM2FmNzYyNDZhIiwic2NvcGUiOiJtaWNyb3Byb2ZpbGUtand0IiwidXBuIjoianVhbiIsImdyb3VwcyI6WyJpbnN0cnVjdG9yIiwib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLXF1YXJrdXMiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfQ.TonKJPkAvHMkEl_cgKiHw9iWpHXA4Sxm7KZrX53_Mb8msrgLtaJHJDsN9n2AfwtnYWOeSZiI_JLYai1rXW3131uqrDlD_QSEBMBpK_8g_RQWnj9ViLW43gKevhdD4qkrxmf5gKssU7d2n0xSteS3Xw_J0QgHp-8PfBfzcs31I7nGcRVl048duVa6NVXLsdnZaMEUkZ0vgWx7e3819XjW_iP01pDU4ax9i-5lHx0huMgdt_Yezkas_imSumIc0k1zu5XYlSJxYx5la_8Lxp-G771DH9dZgncR9IoTGnrscWM7IejtM4qvAcqe0fOsgX07iW6IjMD_dCsGZcXRuoP82A';


const dni = ref<string>('')
const practicante = ref<Practicante | null>(null);
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
            params: { dni: dni.value },
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

.content-table {
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.9em;
    min-width: 400px;
}

.content-table thead tr {
    background-color: #009879;
    color: #ffffff;
    text-align: left;
    font-weight: bold;
}

.content-table th,
.content-table td {
    padding: 12px 15px;
}

.content-table tbody tr {
    border-bottom: 1px solid #dddddd;
}
</style>