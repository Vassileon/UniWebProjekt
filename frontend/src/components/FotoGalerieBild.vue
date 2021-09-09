<template>
  <div class="card column is-3 has-background-grey-lighter m-3">
    <div class="card-header">
      <p class="card-header-title is-centered">
        <!-- Dateinamen ausgeben -->
        {{name}}
      </p>
      <!-- Lösch-Button -->
      <button class="button card-header-icon has-background-grey-light" @click="delFoto">
        <i class="fa fa-times" />
      </button>
    </div>
    <div class="card-content has-text-centered">
      <!-- Bild anzeigen -->
      <figure class="image is-inline-block">
        <img :src="url" />
      </figure>
      <div class="content">
        <foto-star-rating :maxsterne="5" />
      </div>
      <!-- Ort -->
      <div class="content"> {{ort}} </div>
      <!-- Zeitstempel -->
      <div class="has-text-grey"> {{zeit}} </div>
    </div>
  </div>
</template>


<script lang="ts">
import { defineComponent, PropType, ref } from "vue";
import FotoStarRating from "./FotoStarRating.vue";
import { Foto } from "@/services/Foto";

export default defineComponent({
  components: { FotoStarRating },
  name: "FotoGalerieBild",
  props: {
    foto: {
      type: Object as PropType<Foto>, required: true
    }, 
  },
  setup(props, context) {

    function delFoto(){
      context.emit("del-foto", props.foto.id)
    }
    return {
      url: ("/foto/" + props.foto.id), 
      ort: props.foto.ort,
      zeit: props.foto.zeitstempel,
      name: props.foto.dateiname,
      delFoto
    };
  }
});
</script>
