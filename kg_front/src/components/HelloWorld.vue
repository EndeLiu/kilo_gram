<template>
  <div class="container">
  </div>
</template>

<script>
  import * as d3 from 'd3'
export default {
  name: 'HelloWorld',
  data () {
    return {
      links:[],
      nodes:[],
      nodesName:[],
      linksName:[],
      simulation:null,
      scale:1,
      width:800,
      height:500,
      colorList:['#FD7623','#3388B1','#D82952','#F3D737','#409071','#D64E52'],
      testGraph:{
        "nodes": [],
        // "links":[],
        "links": [
          // {"source": "Jack Nicholson", "target": "Emil Eifrem", "value": 5},
        ]
      }
    }
  },
  mounted() {
    this.getGraphData()
  },
  methods: {
    getGraphData(){
      var _this = this
      this.axios.get("person/"+'Tom Cruise')
      // this.axios.get("person/all")
        .then(function (response) {
          _this.testGraph["nodes"] = [response.data]
          _this.initGraph(_this.testGraph)
        })
        .catch(function (error) {
          console.log(error)
        })
    },

    initGraph(data){
      var _this = this
      const links = data.links.map(d => Object.create(d));
      const nodes = data.nodes.map(d => Object.create(d));

      _this.simulation = d3.forceSimulation(nodes)
        .force("link", d3.forceLink(links).id(d => d.id).distance(100))
        .force("collide",d3.forceCollide().radius(()=>30))
        .force("charge", d3.forceManyBody().strength(-30))
        .force("center", d3.forceCenter(_this.width / 2, _this.height / 2));

      const svg = d3.select(".container")
        .append("svg")
        .attr("viewBox", [0, 0, _this.width, _this.height])
        .call(d3.zoom().on("zoom",function () {
          g.attr("transform",d3.event.transform)
        }))

      const positiveMarker = svg.append("marker")
        .attr("id","positiveMarker")
        .attr("orient","auto")
        .attr("stroke-width",2)
        .attr("markerUnits", "strokeWidth")
        .attr("markerUnits", "userSpaceOnUse")
        .attr("viewBox", "0 -5 10 10")
        .attr("refX", 35)
        .attr("refY", 0)
        .attr("markerWidth", 12)
        .attr("markerHeight", 12)
        .append("path")
        .attr("d", "M 0 -5 L 10 0 L 0 5")
        .attr('fill', '#999')
        .attr("stroke-opacity", 0.6);
      const negativeMarker = svg.append("marker")
        .attr("id","negativeMarker")
        .attr("orient","auto")
        .attr("stroke-width",2)
        .attr("markerUnits", "strokeWidth")
        .attr("markerUnits", "userSpaceOnUse")
        .attr("viewBox", "0 -5 10 10")
        .attr("refX", -25)
        .attr("refY", 0)
        .attr("markerWidth", 12)
        .attr("markerHeight", 12)
        .append("path")
        .attr("d", "M 10 -5 L 0 0 L 10 5")
        .attr('fill', '#999')
        .attr("stroke-opacity", 0.6);


      const g = svg.append("g")

      _this.links = g.append("g")
        .attr("stroke", "#999")
        .attr("stroke-opacity", 0.6)
        .selectAll("path")
        .data(links)
        .join("path")
        .attr("marker-end", "url(#positiveMarker)")
        .attr("stroke-width", d => Math.sqrt(d.value))
        .attr("class","link")
        .attr("id",d => d.source+"_"+d.relationship+"_"+d.target)

      _this.linksName = g.append("g")
        .selectAll("text")
        .data(links)
        .join("text")
        .style('text-anchor','middle')
        .style('fill', 'white')
        .style('font-size', '10px')
        .style('font-weight', 'bold')
        .append('textPath')
        .attr(
          'xlink:href',d =>"#"+d.source+"_"+d.relationship+"_"+d.target
        )
        .attr('startOffset','50%')
        .text(function (d) {
          return d.relationship
        });



      _this.nodes = g.append("g")
        .selectAll("circle")
        .data(nodes)
        .join("circle")
        .attr("r", 30)
        .attr("class","node")
        .attr("fill", _this.color)
        .on("click",_this.queryTest)
        .call(_this.drag(_this.simulation));

      _this.nodes.append("title")
        .text(d => d.id);

      _this.nodesName = g.append("g")
        .selectAll("text")
        .data(nodes)
        .join("text")
        .text(function (d) {
          return d.id
        })
        .attr("dx",function () {
          return this.getBoundingClientRect().width/2*(-1)
        })
        .attr("dy",50)
        .attr("class","nodeName")


      _this.simulation.on("tick", () => {

        // _this.links
        //   .attr("d", d => "M "+d.source.x+" "+ d.source.y +" L "+d.target.x+" "+d.target.y);

        _this.links
          .attr("d", function(d){
            if(d.source.x<d.target.x){
              return "M "+d.source.x+" "+ d.source.y +" L "+d.target.x+" "+d.target.y
            }
            else{
              return "M "+d.target.x+" "+ d.target.y +" L "+d.source.x+" "+d.source.y
            }
          })
          .attr("marker-end",function (d) {
            if(d.source.x<d.target.x){
              return "url(#positiveMarker)"
            }
            else{
              return null
            }
          })
          .attr("marker-start",function (d) {
            if(d.source.x<d.target.x){
              return null
            }
            else{
              return "url(#negativeMarker)"
            }
          })




        _this.nodes
          .attr("cx", d => d.x)
          .attr("cy", d => d.y);

        _this.nodesName
          .attr("x",d => d.x)
          .attr("y",d => d.y);
      });

    },

    updateGraph(data){
      var _this = this
      // const links = data.links.map(d => Object.create(d));
      // const nodes = data.nodes.map(d => Object.create(d));
      const links = data.links;
      const nodes = data.nodes;


      _this.links = _this.links
        .data(links)
        .enter()
        .append("path")
        .attr("stroke", "#999")
        .attr("stroke-opacity", 0.6)
        .attr("stroke-width", d => Math.sqrt(d.value))
        .attr("marker-end", "url(#positiveMarker)")
        .merge(_this.links)
        .attr("id",d => d.source+"_"+d.relationship+"_"+d.target)
        .attr("class","link");

      _this.linksName = _this.linksName
        .data(links)
        .enter()
        .append("text")
        .style('text-anchor','middle')
        .style('fill', 'white')
        .style('font-size', '10px')
        .style('font-weight', 'bold')
        .append('textPath')
        .attr(
          'xlink:href',d =>"#"+d.source+"_"+d.relationship+"_"+d.target
        )
        .attr('startOffset','50%')
        .merge(_this.linksName)
        .text(function (d) {
          return d.relationship
        });

      _this.nodes = _this.nodes
        .data(nodes)
        .enter()
        .append("circle")
        .attr("r", 30)
        .attr("class","node")
        .attr("fill", _this.color)
        .merge(_this.nodes)
        .on("click",_this.queryTest)
        .call(_this.drag(_this.simulation));

      _this.nodes.append("title")
        .text(d => d.id);


      _this.nodesName =  _this.nodesName
        .data(nodes)
        .enter()
        .append("text")
        .merge(_this.nodesName)
        .text(function (d) {
          return d.id
        })
        .attr("dx",function () {
          return this.getBoundingClientRect().width/2*(-1)
        })
        .attr("dy",50)
        .attr("class","nodeName")


      _this.simulation.nodes(nodes)
      _this.simulation.force("link").links(links)
      _this.simulation.alpha(1).restart()

    },

    color(d) {
      return this.colorList[d.group]
    },

    drag(simulation) {
      function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
      }

      function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
      }

      function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0);
      }

      return d3.drag()
        .on("start", dragstarted)
        .on("drag", dragged)
        .on("end", dragended);
    },

    queryTest(d){
      var _this = this
      this.axios.get("person/actedby/"+d.id)
        .then(function (response) {
          if(response.status === 200) {
            for(var i=0;i<response.data.length;i++){
              let flag = true
              for(var j=0;j<_this.testGraph.nodes.length;j++){
                if(_this.testGraph.nodes[j].id === response.data[i].id){
                  flag = false
                  break
                }
              }
              if(flag){
                _this.testGraph.nodes.push(response.data[i])
                _this.testGraph.links.push({
                  "source":d.id,
                  "target":response.data[i].id,
                  "value":5,
                  "relationship":"ACTED_IN"
                })
              }
            }
            _this.updateGraph(_this.testGraph)
            console.log(_this.testGraph)
          }
        })
        .catch(function (error) {
          console.log(error)
        })
    }

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  body{
    margin: 0px;
  }

  .container{
    width: 800px;
    height: 500px;
    border: 1px solid #2c3e50;
    border-radius:8px;
    margin-top: 40px;
    margin-left: auto;
    margin-right: auto;
    background: #154360 repeating-linear-gradient(30deg,
    hsla(0, 0%, 100%, .1), hsla(0, 0%, 100%, .1) 15px,
    transparent 0, transparent 30px);
  }
  .node{
    stroke:#fff;
    stroke-width:1;
    cursor: pointer;
  }

  .node:hover{
    stroke-width:5;
  }

  .nodeName{
    fill:white;
  }

</style>
