<template>
  <div class="container">
    <div class="notification is-danger" v-if="errormessage != ''">
      {{ errormessage }}
    </div>
    <!-- Button zum Hinzufügen des nächsten Bildes -->
    <button class="button" v-on:click="addP()">
      <i class="fas fa-camera" />
    </button>
    <!-- Eingabefeld für inkrementelle Suche -->
    <section class="section">
      <input type="text" class="input" v-model="suchfeld" placeholder="Suche" />
    </section>
    <section class="section">
      <div class="columns is-multiline">
        <!-- Hier alle Bilder mit Hilfe der FotoGalerieBild-Komponente anzeigen -->
        <!-- flexibel natürlich - nicht die fünf Beispielbilder hardcoden! -->

        <foto-galerie-bild
          v-for="i in fotos"
          v-bind:key="i"
          :foto="i"
          @del-foto="delFoto($event)"
        />
      </div>
    </section>
    <div>Insgestamt {{ anzahlBilder }} Bilder</div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, Ref, onMounted } from "vue";
import FotoGalerieBild from "./FotoGalerieBild.vue";
import { Foto } from "@/services/Foto";
//import { fotoliste } from "@/services/FotoListe";
import { useFotoStore } from "@/services/FotoStore";

export default defineComponent({
  name: "FotoGalerie",
  components: {
    FotoGalerieBild,
  },
  setup(props, context) {
    const { fotostate, updateFotos, deletFoto } = useFotoStore();
    //const fotos: Ref<Foto[]> = ref([]);
    //let i = 0;
    // function addP() {
    //   if (i < fotostate.fotos.value.length) {
    //     fotos.value.push(fotostate.fotos.get(i));
    //     i++;
    //   } else {
    //     alert("Keine Fotos mehr");
    //   }
    // }
    const suchfeld = ref("");
    const listitems = computed(() => {
      if (suchfeld.value.length < 3) {
        console.log("weniger als 3");
        return fotostate.fotos;
      } else {
        console.log("mehr als 3 zeichen"); 
        return fotostate.fotos.filter((e) =>
          e.ort.toLowerCase().includes(suchfeld.value.toLowerCase())
        );
      }
    });
    function delFoto(id: number): void {
      deletFoto(id);
    }
    onMounted(async () => {
      await updateFotos();
    });
    return {
      //addP,
      suchfeld,
      fotos: listitems,
      delFoto,
      errormessage: ref(fotostate.errormessage),
      anzahlBilder: ref(fotostate.fotos).value.length
    };
  },
});
</script>


<style scoped>
</style>
