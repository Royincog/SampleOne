$(document).ready(function(){
   
$( 'input[type="checkbox"]' ).click( function() 
{
    if( $( 'input[type="checkbox"]:checked' ).length > 0 ) 
    {
         
        $(".col-sm").hide();    
        $( 'input[type="checkbox"]:checked' ).each( function() 
        { 
            // Display ONLY those product_divs that has a match in the data-atr, according to the id of the checkbox.
            $( "div[id ='"+this.id+"']").show();
            console.log(this.id);
            // IT DOESTN WORK WITH SCREEN SIZES.....
            //$( '.product_div[data-screen = ' + this.id + ']' ).fadeIn( 100 );


            // Align filtered products, run function
            
        });
    
}
    else{
        $(".col-sm").show();
    }
})
})