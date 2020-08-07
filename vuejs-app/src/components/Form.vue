<template>
  <div class="basicForm">
    <p>Personnal Information</p>
    <fieldset>
      <div class="form-group">
        <label for="firstName">First Name</label>
        <input
          id="firstName"
          v-model="firstName"
          placeholder="Type your first name"
          class="form-control"
          type="text"
        />
      </div>
      <div class="form-group">
        <label for="lastName">First Name</label>
        <input
          id="lastName"
          v-model="lastName"
          placeholder="Type your last name"
          class="form-control"
          type="text"
        />
      </div>
      <button @click="submit" class="btn btn-primary">Submit</button>
    </fieldset>
    <p v-show="showMsg">{{ msg }}</p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue, Watch } from "vue-property-decorator";
import axios from "axios";
import test from "underscore";
import StringUtils from "underscore.string";

@Component
export default class Form extends Vue {
  firstName: string = "";
  lastName: string = "";
  msg: string = "";
  showMsg: boolean = false;


  @Watch("msg")
  msgChanged(newVal: string) {
    this.msg = newVal;
    this.showMsg = !StringUtils.isBlank(this.msg);
  }

  async submit(): Promise<void> {
    const result = await axios.post(
      "/o/liferay-spring-vuejs/api/services/form",
      { firstName: this.firstName, lastName: this.lastName }
    );
    this.msgChanged("YEAH, it's working. We received : " + JSON.stringify(result.data));
    return;
  }
}
</script>